package com.quickgo.platform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.quickgo.platform.asynctask.AsyncTaskBus;
import com.quickgo.platform.asynctask.Log;
import com.quickgo.platform.asynctask.message.MessageBus;
import com.quickgo.platform.exception.Handler;
import com.quickgo.platform.face.*;
import com.quickgo.platform.model.*;
import com.quickgo.platform.param.Message;
import com.quickgo.platform.param.Parameter;
import com.quickgo.platform.param.Result;
import com.quickgo.platform.param._HashMap;
import com.quickgo.platform.utils.*;
import com.quickgo.platform.view.PdfView;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.dom4j.DocumentException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.quickgo.platform.param.Result.returnInfo;


/**
 *
 * 项目
 *
 * create by huangjie
 * on 2016-07-20
 */
@RestController
@RequestMapping("/project")
@Transactional(readOnly = true)
public class ProjectController {
    @Autowired
    private IProjectService projectService;
    @Autowired
    private IProjectUserService projectUserService;
    @Autowired
    private IModuleService moduleService;
    @Autowired
    private IInterfaceFolderService interfaceFolderService;
    @Autowired
    private IInterfaceService interfaceService;
    @Autowired
    private IShareService shareService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDatadbService datadbService;


    @RequestMapping("/list")
    public Object list(String token) {
        User user = MemoryUtils.getUser(token);
        List<Project> projects = new ArrayList<>();
        if (user != null) {
            projects = projectService.getProjects(user.getId());
        }

        return returnInfo(new _HashMap<>().add("projects", projects));

    }

    /**
     * 查询单个module对应的接口
     *
     * @param id id
     */
    @RequestMapping("/{id}")
    public Object get(@PathVariable String id, String token) {
        Project project = projectService.getProject(id);
        if (project == null || !Project.Status.VALID.equals(project.getStatus())) {
            return new _HashMap<>();
        }
        User user = MemoryUtils.getUser(token);
        if (project.getPermission().equals(Project.Permission.PRIVATE)) {
           if(user == null){
               return  Result.fail("无访问权限");
           }
            if (!user.getId().equals(project.getUserId())) {
                boolean is = projectUserService.checkUserHasProjectPermission(user.getId(), project.getId());
                if(!is){
                    return  Result.fail("无访问权限");
                }
            }
        }
        if (user != null) {
            String editable = projectUserService.getProjectEditable( user.getId(),project.getId());
            project.setEditable(editable);
        } else {
            project.setEditable(ProjectUser.Editable.NO);
        }
        if (project.getId().equalsIgnoreCase("demo")) {
            project.setEditable(ProjectUser.Editable.YES);
        }
        List<Module> modules = moduleService.getModules(id);
        List<InterfaceFolder> folders = null;
        if (modules.size() > 0) {
            //获取该项目下所有文件夹
            folders = interfaceFolderService.getFoldersByProjectId(project.getId());
            Map<String, List<InterfaceFolder>> folderMap = ResultUtils.listToMap(folders, new Handler<InterfaceFolder>() {
                @Override
                public String key(InterfaceFolder item) {
                    return item.getModuleId();
                }
            });
            for (Module module : modules) {
                List<InterfaceFolder> temp = folderMap.get(module.getId());
                if (temp != null) {
                    module.setFolders(temp);
                }
            }

            //获取该项目下所有接口
            List<Interface> interfaces = interfaceService.getInterfacesByProjectId(project.getId());
            Map<String, List<Interface>> interMap = ResultUtils.listToMap(interfaces, new Handler<Interface>() {
                @Override
                public String key(Interface item) {
                    return item.getFolderId();
                }
            });
            for (InterfaceFolder folder : folders) {
                List<Interface> temp = interMap.get(folder.getId());
                if (temp != null) {
                    folder.setChildren(temp);
                }
            }
        } else {
            Module module = createDefaultModule(token, project.getId());
            modules = new ArrayList<>();
            modules.add(module);
        }

        return returnInfo(new _HashMap<>().add("modules", modules).add("project", project));
    }


    @RequestMapping("/{id}/info")
    public Object info(@PathVariable String id) {
        Project project = projectService.getProject(id);
        AssertUtils.isTrue(project != null, "project is null");
        return returnInfo(new _HashMap<>().add("project", project));
    }

    @RequestMapping("/{id}/shares")
    public Object shares(@PathVariable String id) {
        return returnInfo(new _HashMap<>()
                .add("shares", shareService.getSharesByProjectId(id)));
    }


    /**
     * 设置是否常用项目
     *
     * @param id id
     * @param parameter 参数
     */
    @RequestMapping("/{id}/commonly")
    public Object updateCommonlyUsed(@PathVariable String id, Parameter parameter) {
        String userId = MemoryUtils.getUser(parameter).getId();
        String isCommonlyUsed = parameter.getParamString().get("isCommonlyUsed");
        AssertUtils.notNull(isCommonlyUsed, "isCommonlyUsed is null");
        int rs = projectUserService.updateCommonlyUsedProject(id, userId, isCommonlyUsed);
        AssertUtils.isTrue(rs > 0, "操作失败");
        return returnInfo(rs);
    }

    //创建默认模块
    private Module createDefaultModule(String token, String projectId) {
        Module module = new Module();
        module.setLastUpdateTime(new Date().getTime());
        module.setCreateTime(new Date().getTime());
        module.setId(Validate.id());
        module.setProjectId(projectId);
        module.setName("默认模块");
        int rs = moduleService.createModule(module);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        AsyncTaskBus.instance().push(Log.create(token, Log.CREATE_PROJECT, "默认模块", projectId));
        module.addInterfaceFolder(createDefaultFolder(module.getId(), projectId, token));
        return module;
    }

    //创建默认分类
    private InterfaceFolder createDefaultFolder(String moduleId, String projectId, String token) {
        InterfaceFolder folder = new InterfaceFolder();
        folder.setId(Validate.id());
        folder.setName("默认分类");
        folder.setCreateTime(new Date().getTime());
        folder.setModuleId(moduleId);
        folder.setProjectId(projectId);
        int rs = interfaceFolderService.create(folder);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        AsyncTaskBus.instance().push(Log.create(token, Log.CREATE_FOLDER, "默认分类", projectId));
        return folder;
    }


    @RequestMapping("/{id}/users")
    public Object getUsers(@PathVariable String id, Parameter parameter) {
        List<User> users = projectUserService.getUsersByProjectId(id);
        return returnInfo(new _HashMap<>().add("users", users)
                .add("fileAccess", ConfigUtils.getFileAccessURL()));
    }

    @RequestMapping("/create")
    @Transactional(readOnly = false)
    public Object create(String token, Project project) {
        try {
            int rs ;
            User user = MemoryUtils.getUser(token);
            if(user==null){
                return  Result.fail("用户未登录，无权操作数据");
            }
            project.setId(Validate.id());
            project.setCreateTime(new Date().getTime());
            project.setUserId(user.getId());
            project.setStatus(Project.Status.VALID);
            project.setEditable(ProjectUser.Editable.YES);
            ProjectUser projectUser = new ProjectUser();
            BeanUtils.copyProperties(project,projectUser);
            projectUser.setProjectId(project.getId());
            projectUser.setId(Validate.uuid());
            if(StringUtils.isEmpty(project.getName())){
                return  Result.fail("missing name");
            }
            if(StringUtils.isEmpty(project.getUserId())){
                return  Result.fail("missing userId");
            }
            rs = projectService.createProject(project);
            AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
            DataBase dataBase = project.getDataBase();
//            if(StringUtils.isEmpty(project.getDataBase())){
//                throw new IllegalArgumentException("dataBase参数为空");
//            }
//            DataBase dataBase  = (DataBase)JsonUtil.json2obj(project.getDataBase(),DataBase.class);
            rs = datadbService.save(dataBase);
            AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
            int re = projectUserService.createProjectUser(projectUser);
            AssertUtils.isTrue(re > 0, Message.OPER_ERR);
            AsyncTaskBus.instance().push(Log.create(token, Log.CREATE_PROJECT, project.getName(), project.getId()));
        } catch (BeansException e) {
            LogTemplate.error("项目创建失败",e.getStackTrace());
        }
        return returnInfo(new _HashMap<>().add("id",project.getId()));
    }

    private void checkUserHasEditPermission(String projectId, String token) {
        User user = MemoryUtils.getUser(token);
        if(user==null){
            Result.fail("无操作权限");
        }
        boolean permission = projectUserService.checkUserHasProjectPermission(user.getId(), projectId);
        if(!permission){
            Result.fail("无操作权限");
        }
    }

    private String checkUserHasOperatePermission(String projectId, String token) {
        User user = MemoryUtils.getUser(token);
        if(user==null){
            return "无操作权限";
        }
        Project project = projectService.getProject(projectId);
        if(project==null){
            return "项目不存在";
        }
        if(!user.getId().equals(project.getUserId())){
            return "无操作权限";
        }
        return "";
    }


    /**
     * 更新
     *
     * @param id id
     * @param token token
     * @return Object
     */
    @RequestMapping("/update/{id}")
    public Object update(@PathVariable("id") String id, String token,Project projects) {
        this.checkUserHasEditPermission(id, token);
        Project project = new Project();
        BeanUtils.copyProperties(projects, project);
        project.setId(id);
        project.setUserId(null);
        int rs = projectService.updateProject(project);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
//        if(StringUtils.isEmpty(project.getDataBase())){
//            throw new IllegalArgumentException("数据无效");
//        }
//        DataBase dataBase  = (DataBase)JsonUtil.json2obj(project.getDataBase(),DataBase.class);
        DataBase dataBase  = project.getDataBase();
        rs = datadbService.update(dataBase);
        String projectName = projectService.getProjectName(id);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        AsyncTaskBus.instance().push(Log.create(token, Log.UPDATE_PROJECT, projectName, id));
        return returnInfo(rs);
    }

    @RequestMapping("/{id}/transfer")
    public Object transfer(@PathVariable("id") String id, String  userId,String token) {
        if(!StringUtils.isNotBlank(userId)){
            return  Result.fail("missing userId");
        }
        String result = checkUserHasOperatePermission(id, token);
        if(StringUtils.isNotBlank(result)){
            return Result.returnInfo(null,result);
        }
        Project temp = new Project();
        temp.setId(id);
        temp.setUserId(userId);
        int rs = projectService.updateProject(temp);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        String projectName = projectService.getProjectName(id);
        AsyncTaskBus.instance().push(Log.create(token, Log.TRANSFER_PROJECT, projectName, id));
        return returnInfo(rs);
    }


    /**
     * 删除项目
     *
     * @param id id
     * @param token token
     * @return Object
     */
    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable("id") String id, String token) {
        String result = checkUserHasOperatePermission(id, token);
        if(StringUtils.isNotBlank(result)){
            return Result.returnInfo(null,result);
        }
        int rs = projectService.deleteProject(id);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        String projectName = projectService.getProjectName(id);
        AsyncTaskBus.instance().push(Log.create(token, Log.DELETE_PROJECT, projectName, id));
        return returnInfo(rs);
    }

    @RequestMapping("/save")
    public Object save(String id,String token,Project project) {
        if (StringUtils.isEmpty(id)) {
            return create(token,project);
        }
        String result = checkUserHasOperatePermission(id, token);
        if(StringUtils.isNotBlank(result)){
            return Result.returnInfo(null,result);
        }
        return update(id, token,project);
    }

    /**
     * 移动复制
     *
     * @param id id
     * @param copyFolder copyFolder
     * @return Object
     */
    @RequestMapping("/{id}/copymove")
    public Object copyMove(@PathVariable("id") String id,String token, CopyFolder copyFolder) {
        if(copyFolder==null){
           return Result.fail("missing userId");
        }
        //动作
        String action =copyFolder.getAction();
        //类型
        String type = copyFolder.getType();
        //
        String moduleId = copyFolder.getModuleId();
        //
        String folderId = copyFolder.getFolderId();
        //
        String targetId = copyFolder.getTargetId();
        //
        String projectId = copyFolder.getProjectId();
        if (type.equals("api")) {
            AssertUtils.notNull(folderId, "missing folderId");
        }
        int rs = 0;
        if (action.equals("move")) {
            //移动
            if (type.equals("folder")) {
                InterfaceFolder folder = new InterfaceFolder();
                folder.setId(targetId);
                folder.setModuleId(moduleId);
                rs = interfaceFolderService.upadteFolder(folder);
                //String folderName = ServiceFactory.instance().getInterfaceFolderName(targetId);
                //AsyncTaskBus.instance().push(Log.create(token, Log.MOVE_FOLDER,folderName,id));
            } else {
                Interface in = new Interface();
                in.setId(targetId);
                in.setModuleId(moduleId);
                in.setFolderId(folderId);
                rs = interfaceService.upadteInterface(in);

                //String interfaceName = ServiceFactory.instance().getInterfaceName(targetId);
                //AsyncTaskBus.instance().push(Log.create(token, Log.MOVE_INTERFACE,interfaceName,id));
            }
        } else if (action.equals("copy")) {
            //复制
            if (type.equals("folder")) {
                InterfaceFolder folder = interfaceFolderService.getById(targetId);
                folder.setId(Validate.id());
                folder.setCreateTime(new Date().getTime());
                folder.setModuleId(moduleId);
                if(folder.getName()== null){
                    folder.setName("");
                }
                if(!folder.getName().contains("COPY")) {
                    folder.setName(folder.getName() + "_COPY");
                }
                rs = interfaceFolderService.create(folder);

                List<Interface> interfaces = interfaceService.getInterface(targetId);
                if(interfaces!=null && interfaces.size()>0){
                    for(Interface in: interfaces){
                        in.setId(Validate.id());
                        in.setFolderId(folder.getId());
                        in.setModuleId(moduleId);
                        in.setCreateTime(new Date().getTime());
                        in.setLastUpdateTime(new Date().getTime());
                        rs = interfaceService.create(in);
                    }
                }


                //String folderName = ServiceFactory.instance().getInterfaceFolderName(targetId);
                AsyncTaskBus.instance().push(Log.create(token, Log.COPY_FOLDER,"",id));
            } else {
                //接口
                Interface in = interfaceService.getById(targetId);
                AssertUtils.notNull(in, "无效接口Id");
                in.setId(Validate.id());
                in.setFolderId(folderId);
                in.setModuleId(moduleId);
                in.setCreateTime(new Date().getTime());
                in.setLastUpdateTime(new Date().getTime());
                if (in.getName() == null) {
                    in.setName("");
                }
                if (!in.getName().contains("COPY")) {
                    in.setName(in.getName() + "_COPY");
                }
                rs = interfaceService.create(in);
                AsyncTaskBus.instance().push(projectId, Project.Action.COPY_INTERFACE, in.getId(), token, in.getFolderId());
                //String interfaceName = ServiceFactory.instance().getInterfaceName(targetId);
                //AsyncTaskBus.instance().push(Log.create(token, Log.COPY_INTERFACE,interfaceName,id));
            }
        }
        AssertUtils.isTrue(rs > 0, "操作失败");
        return returnInfo(rs);
    }

    /**
     * 邀请成员
     *
     * @param id
     * @param project
     * @return
     */
    @RequestMapping("/{id}/invite")
    public Object invite(@PathVariable("id") String id,String token,Project project) {
        User user = MemoryUtils.getUser(token);
        ProjectUser pu = new ProjectUser();
        pu.setId(Validate.id());
        pu.setUserId(project.getUserId());
        if(!StringUtils.isNotBlank(pu.getUserId())){
            return returnInfo(null,"missing userId");
        }
        boolean is = projectUserService.checkUserHasProjectPermission(id, project.getUserId());
        if(!is){
            return returnInfo(null,"用户已存在该项目中");
        }
        if(pu.getUserId().equals(user.getId())){
            return returnInfo(null,"不能邀请自己");
        }
        pu.setCreateTime(new Date().getTime());
        pu.setStatus(ProjectUser.Status.PENDING);
        pu.setEditable(ProjectUser.Editable.NO);
        pu.setProjectId(id);
        int rs = projectUserService.createProjectUser(pu);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        MessageBus.instance().push("PROJECT.INVITE", pu.getProjectId(), new String[]{pu.getUserId()});
        return returnInfo(pu.getId());
    }

    /**
     * 邀请成员
     *
     * @param id id
     * @param token token
     * @param email email
     * @return Object
     */
    @RequestMapping("/{id}/invite/email")
    public Object inviteByEmail(@PathVariable("id") String id, String token,String email) {
        String userId = userService.getUserIdByEmail(email);
        if(StringUtils.isEmpty(userId)){
            return  returnInfo("该邮箱未注册");
        }
        User user = MemoryUtils.getUser(token);
        if(userId.equals(user.getId())){
            returnInfo("不能邀请自己");
        }
        boolean is = projectUserService.checkProjectUserExists(id, userId);
        if(is){
            returnInfo("用户已存在该项目中");
        }
        ProjectUser pu = new ProjectUser();
        pu.setId(Validate.id());
        pu.setUserId(userId);
        pu.setProjectId(id);
        pu.setEditable(ProjectUser.Editable.YES);
        if(!StringUtils.isNotBlank(pu.getProjectId())){
            return returnInfo("missing projectId");
        }
        pu.setCreateTime(new Date().getTime());
        pu.setStatus(ProjectUser.Status.PENDING);
        int rs = projectUserService.createProjectUser(pu);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        MessageBus.instance().push("PROJECT.INVITE", pu.getProjectId(), new String[]{pu.getUserId()});
        return returnInfo(pu.getId());
    }


    /**
     * 接受邀请
     *
     * @param inviteId
     * @return
     */
    @RequestMapping("/{id}/pu/{inviteId}/accept")
    public Object acceptInvite(@PathVariable("inviteId") String inviteId) {
        ProjectUser pu = new ProjectUser();
        pu.setId(inviteId);
        pu.setStatus(ProjectUser.Status.ACCEPTED);
        int rs = projectUserService.createProjectUser(pu);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        return returnInfo(rs);
    }

    /**
     * 拒绝邀请
     */
    @RequestMapping("/{id}/pu/{inviteId}/refuse")
    public Object acceptRefuse(@PathVariable("inviteId") String inviteId) {
        ProjectUser pu = new ProjectUser();
        pu.setId(inviteId);
        pu.setStatus(ProjectUser.Status.REFUSED);
        int rs = projectUserService.createProjectUser(pu);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        MessageBus.instance().push("PROJECT.INVITE.REFUSE", pu.getProjectId(), pu.getUserId());
        return returnInfo(rs);
    }


    /**
     * 移除成员
     *
     * @param userId    userId
     * @param id        projectId
     * @param token
     * @return
     */
    @RequestMapping("/{id}/pu/{userId}")
    public Object removeMember(@PathVariable("id") String id, @PathVariable("userId") String userId, String token) {
        String result = checkUserHasOperatePermission(id, token);
        if(StringUtils.isNotBlank(result)){
            return Result.returnInfo(null,result);
        }
        Project project = projectService.getProject(id);
        if(project==null){
           return returnInfo(null,"项目不存在");
        }
        boolean isExist = project.getUserId().equals(userId);
        if(isExist){
            return  Result.returnInfo(null,"不能移除自己");
        }
        User temp = MemoryUtils.getUser(token);
        if(!temp.getId().equals(project.getUserId())){
            return  Result.returnInfo(null,"无操作权限");
        }
        int rs = projectUserService.deleteProjectUser(id, userId);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        return returnInfo(rs);
    }


    /**
     * 设置是否可编辑
     *
     * @param projectId 项目id
     * @param userId
     * @param editable
     * @param token
     * @return
     */
    @RequestMapping("/{id}/pu/{userId}/{editable}")
    public Object editProjectEditable(@PathVariable("id") String projectId, @PathVariable("userId") String userId,
                                   @PathVariable("editable") String editable, String token) {
        AssertUtils.isTrue(ProjectUser.Editable.YES.equals(editable) || ProjectUser.Editable.NO.equals(editable), "参数错误");
        String result = checkUserHasOperatePermission(projectId, token);
        if(StringUtils.isNotBlank(result)){
            return Result.returnInfo(null,result);
        }
        int rs = projectUserService.updateProjectUserEditable(projectId, userId, editable);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        return returnInfo(rs);
    }

    /**
     * 退出项目
     *
     * @param id id
     * @param token token
     */
    @RequestMapping("/{id}/quit")
    public Object quit(@PathVariable("id") String id, String token) {
        Project project = projectService.getProject(id);
        AssertUtils.notNull(project, "project not exists");
        String userId = MemoryUtils.getUser(token).getId();
        AssertUtils.isTrue(!project.getUserId().equals(userId), "项目所有人不能退出项目");
        int rs = projectUserService.deleteProjectUser(id, userId);
        AssertUtils.isTrue(rs > 0, Message.OPER_ERR);
        return returnInfo(rs);
    }


    /**
     * 项目导出
     *
     * @param id id
     * @return parameter 参数
     * @throws DocumentException DocumentException
     * @throws IOException       IOException
     */
    @RequestMapping("/{id}/export")
    public Object export(@PathVariable("id") String id,String token) throws DocumentException, IOException, com.itextpdf.text.DocumentException {
        Project project = projectService.getProject(id);
        List<Module> modules = getModulesByProjectId(project, token);
        AssertUtils.notNull(modules, "该项目不存在或已下线");
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            BaseFont font = BaseFont.createFont("FZLTCXHJW.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font moduleFont = new Font(font, 24f, Font.BOLD, BaseColor.BLACK);
            Font folderFont = new Font(font, 20f, Font.BOLD, new BaseColor(66, 66, 66));
            Font apiName = new Font(font, 18f, Font.BOLD, new BaseColor(66, 66, 66));
            Font subtitle = new Font(font, 14f, Font.BOLD, BaseColor.BLACK);
            Font apiFont = new Font(font, 10f, Font.BOLD, new BaseColor(66, 66, 66));
            Paragraph temp = new Paragraph(project.getName(), new Font(font, 32f, Font.BOLD, BaseColor.BLACK));
            document.open();
            document.add(Chunk.NEWLINE);
            temp.setAlignment(Element.ALIGN_CENTER);
            document.add(temp);
            writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
            //方正兰亭

            if (org.apache.commons.lang3.StringUtils.isNotBlank(project.getDetails())) {
                document.add(new Paragraph(project.getDetails(), apiFont));
            }
            if (StringUtils.isNotBlank(project.getEnvironments())) {
                try {
                    JSONArray arr = JSON.parseArray(project.getEnvironments());
                    if (arr.size() > 0) {
                        document.add(new Paragraph("全局环境变量", apiName));
                        document.add(new Paragraph("环境变量运行在URL中,你可以配置多个(线上、灰度、开发)环境变量。在URL中使用方式$变量名$,例：\n" +
                                "线上环境：prefix => http://www.xiaoyaoji.com.cn\n" +
                                "则\n" +
                                "请求URL：$prefix$/say => http://www.xiaoyaoji.com.cn/say\n\n", apiFont));
                        for (int i = 0; i < arr.size(); i++) {
                            JSONObject item = arr.getJSONObject(i);
                            String environmentName = item.getString("name");
                            Paragraph p = new Paragraph(environmentName, subtitle);
                            p.setPaddingTop(10);
                            document.add(p);
                            try {
                                JSONArray vars = item.getJSONArray("vars");
                                if (vars.size() > 0) {
                                    for (int v = 0; v < vars.size(); v++) {
                                        JSONObject var = vars.getJSONObject(v);
                                        document.add(new Paragraph(var.getString("name") + "    " + var.getString("value"), apiFont));
                                    }
                                }
                            } catch (Exception e) {
                                //  e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
            for (int m = 0; m < modules.size(); m++) {
                Module module = modules.get(m);
                document.add(new Paragraph(module.getName(), moduleFont));
                createUpdateTimeCell(document, DateUtils.toStr(new Date(module.getLastUpdateTime())), apiFont);

                Paragraph cTitle = new Paragraph(module.getName(), moduleFont);
                Chapter chapter = new Chapter(cTitle, m + 1);
                List<InterfaceFolder> folders = module.getFolders();
                for (int f = 0; f < folders.size(); f++) {
                    InterfaceFolder folder = folders.get(f);
                    Section section = chapter.addSection(new Paragraph(folder.getName(), folderFont));
                    section.setIndentationRight(0);
                    section.setIndentationLeft(0);
                    if (m > 2) {
                        section.setBookmarkOpen(false);
                    }
                    //section.add(new Paragraph());
                    List<Interface> ins = folder.getChildren();
                    for (int i = 0; i < ins.size(); i++) {
                        Interface in = ins.get(i);
                        section.addSection(new Paragraph(in.getName(), apiName));
                        section.add(new Paragraph("基本信息", subtitle));
                        createUpdateTimeCell(section, DateUtils.toStr(new Date(in.getLastUpdateTime())), apiFont);
                        section.add(new Paragraph("请求类型：" + in.getProtocol(), apiFont));
                        section.add(new Paragraph("请求地址：" + in.getUrl(), apiFont));
                        if ("HTTP".equals(in.getProtocol())) {
                            section.add(new Paragraph("请求方式：" + in.getRequestMethod(), apiFont));
                            section.add(new Paragraph("数据类型：" + in.getDataType(), apiFont));
                            section.add(new Paragraph("响应类型：" + in.getContentType(), apiFont));
                        }
                        section.add(new Paragraph(in.getDescription(), apiFont));
                        String requestHeader = in.getRequestHeaders();
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(requestHeader)) {
                            List<RequestResponseArgs> requestHeaders = JSON.parseObject(requestHeader, new TypeReference<List<RequestResponseArgs>>() {
                            });
                            if (requestHeaders.size() > 0) {
                                Paragraph p = new Paragraph("请求头", subtitle);
                                section.add(p);
                                PdfPTable table = new PdfPTable(4);
                                decorateTable(table);
                                table.addCell(createHeaderCell("参数名称", apiFont));
                                table.addCell(createHeaderCell("是否必须", apiFont));
                                table.addCell(createHeaderCell("描述", apiFont));
                                table.addCell(createHeaderCell("默认值", apiFont));
                                addCells(table, requestHeaders, "requestHeaders", apiFont);
                                section.add(table);
                            }
                        }

                        String requestArg = in.getRequestArgs();
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(requestArg)) {
                            List<RequestResponseArgs> requestArgs = JSON.parseObject(requestArg, new TypeReference<List<RequestResponseArgs>>() {
                            });
                            if (requestArgs.size() > 0) {
                                section.add(new Paragraph("请求参数", subtitle));
                                PdfPTable table = new PdfPTable(5);
                                decorateTable(table);
                                table.addCell(createHeaderCell("参数名称", apiFont));
                                table.addCell(createHeaderCell("是否必须", apiFont));
                                table.addCell(createHeaderCell("类型", apiFont));
                                table.addCell(createHeaderCell("描述", apiFont));
                                table.addCell(createHeaderCell("默认值", apiFont));
                                addCells(table, requestArgs, "requestArgs", apiFont);
                                section.add(table);
                            }
                        }
                        String responseArg = in.getResponseArgs();
                        if (org.apache.commons.lang3.StringUtils.isNotBlank(responseArg)) {
                            List<RequestResponseArgs> responseArgs = JSON.parseObject(responseArg, new TypeReference<List<RequestResponseArgs>>() {
                            });
                            if (responseArgs.size() > 0) {
                                section.add(new Paragraph("响应数据", subtitle));
                                PdfPTable table = new PdfPTable(4);
                                decorateTable(table);
                                table.addCell(createHeaderCell("参数名称", apiFont));
                                table.addCell(createHeaderCell("是否必须", apiFont));
                                table.addCell(createHeaderCell("数据类型", apiFont));
                                table.addCell(createHeaderCell("描述", apiFont));
                                addCells(table, responseArgs, "responseArgs", apiFont);
                                section.add(table);
                            }
                        }


                        if (org.apache.commons.lang3.StringUtils.isNotBlank(in.getExample())) {
                            section.add(new Paragraph("例子", subtitle));
                            section.add(new Paragraph(in.getExample()));
                        }

                        //document.newPage();
                        section.add(Chunk.NEWLINE);
                        section.add(Chunk.NEWLINE);
                        section.add(Chunk.NEWLINE);
                        //section.newPage();
                    }
                }
                document.add(chapter);
            }
            document.close();
            byte[] data = baos.toByteArray();
            AsyncTaskBus.instance().push(Log.create(token, Log.EXPORT_PROJECT, project.getName(), project.getId()));
            return returnInfo(new PdfView(data,project.getName()+".pdf"));
        } finally {
            //document.close();
            IOUtils.closeQuietly(baos);
        }
    }


    //创建时间更新
    private void createUpdateTimeCell(Document document, String text, Font font) throws DocumentException, com.itextpdf.text.DocumentException {
        Paragraph lastUpdateTime = new Paragraph("更新时间:" + text, font);
        lastUpdateTime.setIndentationRight(0);
        lastUpdateTime.setPaddingTop(0);
        lastUpdateTime.setAlignment(Element.ALIGN_RIGHT);
        document.add(lastUpdateTime);
    }

    //创建更新时间
    private void createUpdateTimeCell(Section element, String text, Font font) throws DocumentException {
        Paragraph lastUpdateTime = new Paragraph("更新时间:" + text, font);
        lastUpdateTime.setIndentationRight(0);
        lastUpdateTime.setPaddingTop(0);
        lastUpdateTime.setAlignment(Element.ALIGN_RIGHT);
        element.add(lastUpdateTime);
    }

    //创建table 列
    private PdfPCell createCell(String text, Font font) {
        if (text == null)
            text = " ";
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        return cell;
    }

    //创建table头
    private PdfPCell createHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(8);
        cell.setBackgroundColor(new BaseColor(204, 204, 204));
        return cell;
    }

    //添加列
    private void addCells(PdfPTable table, List<RequestResponseArgs> list, String type, Font font) {
        for (RequestResponseArgs item : list) {
            table.addCell(createCell(item.getName(), font));
            if (org.apache.commons.lang3.StringUtils.isBlank(item.getRequire())) {
                table.addCell(createCell("false", font));
            } else {
                table.addCell(createCell(item.getRequire(), font));
                //table.addCell(new Phrase(getText(item.getRequire()),font));
            }
            if (type.equals("requestArgs") || type.equals("responseArgs")) {
                table.addCell(createCell(item.getType(), font));
            }
            table.addCell(createCell(item.getDescription(), font));
            if (!type.equals("responseArgs")) {
                table.addCell(createCell(item.getDefaultValue(), font));
            }
            addCells(table, item.getChildren(), type, font);
        }
    }

    //设置table样式
    private void decorateTable(PdfPTable table) {
        table.setHeaderRows(1);
        table.setFooterRows(1);
        table.setComplete(true);
        table.setSkipFirstHeader(true);
        table.setWidthPercentage(100);
        table.setSpacingAfter(10);
        table.setSpacingBefore(10);
    }

    private List<Module> getModulesByProjectId(Project project, String token) {

        if (project == null || !Project.Status.VALID.equals(project.getStatus())) {
            return null;
        }
        boolean has = projectUserService.checkUserHasProjectPermission(MemoryUtils.getUser(token).getId(), project.getId());
        AssertUtils.isTrue(has, "无访问权限");
        List<Module> modules = moduleService.getModules(project.getId());
        List<InterfaceFolder> folders = null;
        if (modules.size() > 0) {
            // 获取该项目下所有文件夹
            folders = interfaceFolderService.getFoldersByProjectId(project.getId());
            Map<String, List<InterfaceFolder>> folderMap = ResultUtils.listToMap(folders, new Handler<InterfaceFolder>() {
                @Override
                public String key(InterfaceFolder item) {
                    return item.getModuleId();
                }
            });
            for (Module module : modules) {
                List<InterfaceFolder> temp = folderMap.get(module.getId());
                if (temp != null) {
                    module.setFolders(temp);
                }
            }

            // 获取该项目下所有接口
            List<Interface> interfaces = interfaceService.getInterfacesByProjectId(project.getId());
            Map<String, List<Interface>> interMap = ResultUtils.listToMap(interfaces, new Handler<Interface>() {
                @Override
                public String key(Interface item) {
                    return item.getFolderId();
                }
            });
            for (InterfaceFolder folder : folders) {
                List<Interface> temp = interMap.get(folder.getId());
                if (temp != null) {
                    folder.setChildren(temp);
                }
            }
        }
        return modules;
    }


}
