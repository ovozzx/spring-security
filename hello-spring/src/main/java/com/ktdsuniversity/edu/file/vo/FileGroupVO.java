package com.ktdsuniversity.edu.file.vo;

/**
 * @TableName FILE_GROUP
 * @TableComment null
 */
public class FileGroupVO {

    /**
     * @ColumnName FILE_GROUP_ID
     * @ColumnType VARCHAR2(20)
     * @ColumnComment null
     */
    private String fileGroupId;

    /**
     * @ColumnName FILE_COUNT
     * @ColumnType NUMBER(3, 0)
     * @ColumnComment null
     */
    private int fileCount;

    public String getFileGroupId() {
        return this.fileGroupId;
    }
    
    public void setFileGroupId(String fileGroupId) {
        this.fileGroupId = fileGroupId;
    }
    
    public int getFileCount() {
        return this.fileCount;
    }
    
    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }
    
    @Override
    public String toString() {
        return "FileGroupVO(fileGroupId: " + fileGroupId + ", fileCount: " + fileCount + ", )";
    }
}