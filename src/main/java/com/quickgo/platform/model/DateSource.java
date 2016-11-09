/**
 * 
 */
package com.quickgo.platform.model;



/**
 * 生成方案Entity
 * 
 * @version 2013-10-15
 */
public class DateSource extends DataEntity<DateSource> {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name; 	// 名称
	private String userName;		// 用户名
	private String password;		//密码
	private String flag;		//标志

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DateSource() {
		super();
	}

	public DateSource(String id){
		super(id);
	}
}


