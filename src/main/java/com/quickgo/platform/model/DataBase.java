/**
 * 
 */
package com.quickgo.platform.model;


import java.io.Serializable;

/**
 * 生成方案Entity
 * 
 * @version 2013-10-15
 */
public class DataBase implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String dbPath;
	private String dbPort;
	private String dbName; 	// 名称
	private String userName; // 用户名
	private String password; //密码
	private Long createTime;
	private Long updateTime;
	private String delFlag;	 //标志


	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}

	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
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

	public DataBase() {
		super();
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDbName() {
		return dbName;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}


