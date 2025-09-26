package com.ktdsuniversity.edu.member.vo;

/**
 * @TableName MEMBER
 * @TableComment null
 */
public class MemberVO {

    /**
     * @ColumnName EMAIL
     * @ColumnType VARCHAR2(100)
     * @ColumnComment null
     */
    private String email;

    /**
     * @ColumnName NAME
     * @ColumnType VARCHAR2(100)
     * @ColumnComment null
     */
    private String name;

    /**
     * @ColumnName PASSWORD
     * @ColumnType VARCHAR2(100)
     * @ColumnComment null
     */
    private String password;

    /**
     * @ColumnName SALT
     * @ColumnType VARCHAR2(100)
     * @ColumnComment null
     */
    private String salt;

    /**
     * @ColumnName LOGIN_FAIL_CNT
     * @ColumnType NUMBER(3, 0)
     * @ColumnComment null
     */
    private int loginFailCnt;

    /**
     * @ColumnName BLOCK_YN
     * @ColumnType CHAR(1)
     * @ColumnComment null
     */
    private String blockYn;

    /**
     * @ColumnName LATEST_LOGIN_BLOCK_DATE
     * @ColumnType DATE
     * @ColumnComment null
     */
    private String latestLoginBlockDate;

    /**
     * @ColumnName LATEST_LOGIN_FAIL_DATE
     * @ColumnType DATE
     * @ColumnComment null
     */
    private String latestLoginFailDate;

    /**
     * @ColumnName LATEST_LOGIN_SUCCESS_DATE
     * @ColumnType DATE
     * @ColumnComment null
     */
    private String latestLoginSuccessDate;

    /**
     * @ColumnName DEL_YN
     * @ColumnType CHAR(1)
     * @ColumnComment null
     */
    private String delYn;

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getSalt() {
        return this.salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    public int getLoginFailCnt() {
        return this.loginFailCnt;
    }
    
    public void setLoginFailCnt(int loginFailCnt) {
        this.loginFailCnt = loginFailCnt;
    }
    
    public String getBlockYn() {
        return this.blockYn;
    }
    
    public void setBlockYn(String blockYn) {
        this.blockYn = blockYn;
    }
    
    public String getLatestLoginBlockDate() {
        return this.latestLoginBlockDate;
    }
    
    public void setLatestLoginBlockDate(String latestLoginBlockDate) {
        this.latestLoginBlockDate = latestLoginBlockDate;
    }
    
    public String getLatestLoginFailDate() {
        return this.latestLoginFailDate;
    }
    
    public void setLatestLoginFailDate(String latestLoginFailDate) {
        this.latestLoginFailDate = latestLoginFailDate;
    }
    
    public String getLatestLoginSuccessDate() {
        return this.latestLoginSuccessDate;
    }
    
    public void setLatestLoginSuccessDate(String latestLoginSuccessDate) {
        this.latestLoginSuccessDate = latestLoginSuccessDate;
    }
    
    public String getDelYn() {
        return this.delYn;
    }
    
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }
    
    @Override
    public String toString() {
        return "MemberVO(email: " + email + ", name: " + name + ", password: " + password + ", salt: " + salt + ", loginFailCnt: " + loginFailCnt + ", blockYn: " + blockYn + ", latestLoginBlockDate: " + latestLoginBlockDate + ", latestLoginFailDate: " + latestLoginFailDate + ", latestLoginSuccessDate: " + latestLoginSuccessDate + ", delYn: " + delYn + ", )";
    }
}