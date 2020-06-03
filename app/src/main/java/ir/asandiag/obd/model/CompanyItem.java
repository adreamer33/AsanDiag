package ir.asandiag.obd.model;

public class CompanyItem {
    private int id;
    private String companyName;
    private String companyDesc;

    public CompanyItem(int id, String companyName, String companyDesc) {
        this.id = id;
        this.companyName = companyName;
        this.companyDesc = companyDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public void setCompanyDesc(String companyDesc) {
        this.companyDesc = companyDesc;
    }
}
