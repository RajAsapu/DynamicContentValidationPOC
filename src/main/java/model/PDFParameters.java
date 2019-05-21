package model;


import com.fasterxml.jackson.annotation.JsonProperty;

//@Getter
//@Setter
//@ToString
public class PDFParameters {
    @JsonProperty("Name:")
    public String name;

    @JsonProperty("Business Name:")
    public String businessName;

    @JsonProperty("Address:")
    public String address;

    @JsonProperty("City:")
    public String city;

    @JsonProperty("State:")
    public String state;

    @JsonProperty("Zip code:")
    public String zipCode;

    @JsonProperty("Occupation:")
    public String occupation;

    @JsonProperty("Telephone number:")
    public String telephoneNumber;

    @JsonProperty("Email address:")
    public String emailAddress;

    @JsonProperty("Policy number:")
    public String policyNumber;

    @JsonProperty("Total cost of policy:")
    public String totalCostOfPolicy;

    @JsonProperty("Optional terrorism coverage:")
    public String optionalTerrorismCoverage;

    @JsonProperty("Form of business:")
    public String formOfBusiness;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getTotalCostOfPolicy() {
        return totalCostOfPolicy;
    }

    public void setTotalCostOfPolicy(String totalCostOfPolicy) {
        this.totalCostOfPolicy = totalCostOfPolicy;
    }

    public String getOptionalTerrorismCoverage() {
        return optionalTerrorismCoverage;
    }

    public void setOptionalTerrorismCoverage(String optionalTerrorismCoverage) {
        this.optionalTerrorismCoverage = optionalTerrorismCoverage;
    }

    public String getFormOfBusiness() {
        return formOfBusiness;
    }

    public void setFormOfBusiness(String formOfBusiness) {
        this.formOfBusiness = formOfBusiness;
    }

    @Override
    public String toString() {
        return "PDFParameters{" +
                "name='" + name + '\'' +
                ", businessName='" + businessName + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", occupation='" + occupation + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", policyNumber='" + policyNumber + '\'' +
                ", totalCostOfPolicy='" + totalCostOfPolicy + '\'' +
                ", optionalTerrorismCoverage='" + optionalTerrorismCoverage + '\'' +
                ", formOfBusiness='" + formOfBusiness + '\'' +
                '}';
    }
}
