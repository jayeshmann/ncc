package com.tsa.NCC_dte_punjab.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tsa.NCC_dte_punjab.builders.CadetDetailsBuilder;

public class CadetDetailsModels {
    @SerializedName("expiry_date")
    private String expiryDate;
    private String anoID;
    @Expose
    private String address;
    @SerializedName("affil_reg_no")
    private String affilRegNo;
    @SerializedName("ano_recom_status")
    private String anoRecomStatus;
    @SerializedName("bathroom_available")
    private String bathroomAvailable;
    @SerializedName("aadhaar_no")
    private String aadhaarNo;
    @SerializedName("acc_no")
    private String accNo;
    @SerializedName("bathroom_images")
    private String bathroomImages;
    @SerializedName("adhaar_card_image")
    private String adhaarCardImage;
    @SerializedName("battalion_name")
    private String battalionName;
    @Expose
    private String city;
    @SerializedName("bank_name")
    private String bankName;
    @Expose
    private String mClass;
    @SerializedName("class_id")
    private String classId;
    @SerializedName("battalion_id")
    private String battalionId;
    @SerializedName("co_aprov_status")
    private String coAprovStatus;
    @SerializedName("blood_group")
    private String bloodGroup;
    @SerializedName("cadet_id")
    private String cadetId;
    @SerializedName("collg_school_type")
    private String collgSchoolType;
    @Expose
    private String department;
    @SerializedName("directorate_id")
    private String directorateId;
    @SerializedName("class_name")
    private String className;
    @Expose
    private String dob;
    @Expose
    private String email;
    @Expose
    private String convicted;
    @SerializedName("conviction_circumstances")
    private String convictionCircumstances;
    @SerializedName("conviction_documents")
    private String convictionDocuments;
    @SerializedName("created_date")
    private String createdDate;
    @SerializedName("from_date")
    private String fromDate;
    @Expose
    private String gender;
    @SerializedName("dismiss_erlier")
    private String dismissErlier;
    @SerializedName("district_id")
    private String districtId;
    @SerializedName("district_name")
    private String districtName;
    @SerializedName("hq_dg_id")
    private String hqDgId;
    @SerializedName("dob_cert_image")
    private String dobCertImage;
    @SerializedName("hq_name")
    private String hqName;
    @SerializedName("enrolled_erlier")
    private String enrolledErlier;
    @SerializedName("enrollment_year")
    private String enrollmentYear;
    @SerializedName("exp_department")
    private String expDepartment;
    @SerializedName("father_name")
    private String fatherName;
    @Expose
    private String id;
    @SerializedName("instt_doc")
    private String insttDoc;
    @SerializedName("instt_name")
    private String insttName;
    @SerializedName("hq_id")
    private String hqId;
    @SerializedName("instt_type")
    private String insttType;
    @SerializedName("management_type")
    private String managementType;
    @SerializedName("identification_mark1")
    private String identificationMark1;
    @SerializedName("identification_mark2")
    private String identificationMark2;
    @SerializedName("ifsc_code")
    private String ifscCode;
    @SerializedName("indemnity_bond_image")
    private String indemnityBondImage;
    @SerializedName("NCC_enrolled_students")
    private String nCCEnrolledStudents;
    @SerializedName("instt_id")
    private String insttId;
    @SerializedName("noc_by_society_doc")
    private String nocBySocietyDoc;
    @SerializedName("parade_store_available")
    private String paradeStoreAvailable;
    @SerializedName("is_deleted")
    private String isDeleted;
    @SerializedName("kin_address")
    private String kinAddress;
    @SerializedName("kin_name")
    private String kinName;
    @SerializedName("kin_relationship")
    private String kinRelationship;
    @SerializedName("kin_telephone")
    private String kinTelephone;
    @Expose
    private String landmark;
    @Expose
    private String location;
    @SerializedName("physical_mail_date")
    private String physicalMailDate;
    @SerializedName("marital_status")
    private String maritalStatus;
    @Expose
    private String marks;
    @SerializedName("medical_cert_image")
    private String medicalCertImage;
    @SerializedName("member_regiment_fee_receipt_image")
    private String memberRegimentFeeReceiptImage;
    @Expose
    private String mobile;
    @SerializedName("mother_name")
    private String motherName;
    @SerializedName("physical_mail_status")
    private String physicalMailStatus;
    @Expose
    private String name;
    @Expose
    private String nationality;
    @SerializedName("ncc_exp")
    private String nccExp;
    @SerializedName("ncc_jointype_id")
    private String nccJointypeId;
    @SerializedName("ncc_no")
    private String nccNo;
    @SerializedName("ncc_year")
    private String nccYear;
    @SerializedName("nearest_police_station")
    private String nearestPoliceStation;
    @SerializedName("nearest_railway_station")
    private String nearestRailwayStation;
    @SerializedName("physical_test_status")
    private String physicalTestStatus;
    @SerializedName("nomination_form_image")
    private String nominationFormImage;
    @SerializedName("pan_card_image")
    private String panCardImage;
    @SerializedName("pan_no")
    private String panNo;
    @SerializedName("principal_aprov_status")
    private String principalAprovStatus;
    @SerializedName("parent_dec_image")
    private String parentDecImage;
    @SerializedName("parents_annual_income")
    private String parentsAnnualIncome;
    @Expose
    private String password;
    @SerializedName("principal_id")
    private String principalId;
    @SerializedName("short_range_available")
    private String shortRangeAvailable;
    @Expose
    private String specialization;
    @Expose
    private String pin;
    @SerializedName("teacher_available")
    private String teacherAvailable;
    @SerializedName("to_date")
    private String toDate;
    @SerializedName("profile_image")
    private String profileImage;
    @SerializedName("reg_using")
    private String regUsing;
    @SerializedName("regiment_fee_receipt_image")
    private String regimentFeeReceiptImage;
    @SerializedName("register_id")
    private String registerId;
    @SerializedName("registration_no")
    private String registrationNo;
    @SerializedName("result_for")
    private String resultFor;
    @SerializedName("result_status")
    private String resultStatus;
    @SerializedName("sc_reg_no")
    private String scRegNo;
    @SerializedName("school_collage")
    private String schoolCollage;
    @SerializedName("school_name")
    private String schoolName;
    @SerializedName("total_student")
    private String totalStudent;
    @SerializedName("signature_image")
    private String signatureImage;
    @SerializedName("total_vaccancy")
    private String totalVaccancy;
    @SerializedName("state_id")
    private String stateId;
    @SerializedName("state_name")
    private String stateName;
    @Expose
    private String status;
    @Expose
    private String step;
    @Expose
    private String stream;
    @Expose
    private String taluka;
    @SerializedName("year_establishment")
    private String yearEstablishment;

    public String getAnoID() {
        return anoID;
    }

    public void setAnoID(String anoID) {
        this.anoID = anoID;
    }

    public String getExpiryDate() {
        return expiryDate;
    }
    @SerializedName("unique_login_id")
    private String uniqueLoginId;
    @SerializedName("willing_to_be_enrolled")
    private String willingToBeEnrolled;
    @Expose
    private String wing;

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdhaarCardImage() {
        return adhaarCardImage;
    }

    public void setAdhaarCardImage(String adhaarCardImage) {
        this.adhaarCardImage = adhaarCardImage;
    }

    public String getAffilRegNo() {
        return affilRegNo;
    }

    public void setAffilRegNo(String affilRegNo) {
        this.affilRegNo = affilRegNo;
    }

    public String getAnoRecomStatus() {
        return anoRecomStatus;
    }

    public void setAnoRecomStatus(String anoRecomStatus) {
        this.anoRecomStatus = anoRecomStatus;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBathroomAvailable() {
        return bathroomAvailable;
    }

    public void setBathroomAvailable(String bathroomAvailable) {
        this.bathroomAvailable = bathroomAvailable;
    }

    public String getBathroomImages() {
        return bathroomImages;
    }

    public void setBathroomImages(String bathroomImages) {
        this.bathroomImages = bathroomImages;
    }

    public String getBattalionId() {
        return battalionId;
    }

    public void setBattalionId(String battalionId) {
        this.battalionId = battalionId;
    }

    public String getBattalionName() {
        return battalionName;
    }

    public void setBattalionName(String battalionName) {
        this.battalionName = battalionName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCadetId() {
        return cadetId;
    }

    public void setCadetId(String cadetId) {
        this.cadetId = cadetId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getmClass() {
        return mClass;
    }

    public void setClass(String mclass) {
        this.mClass = mclass;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCoAprovStatus() {
        return coAprovStatus;
    }

    public void setCoAprovStatus(String coAprovStatus) {
        this.coAprovStatus = coAprovStatus;
    }

    public String getCollgSchoolType() {
        return collgSchoolType;
    }

    public void setCollgSchoolType(String collgSchoolType) {
        this.collgSchoolType = collgSchoolType;
    }

    public String getConvicted() {
        return convicted;
    }

    public void setConvicted(String convicted) {
        this.convicted = convicted;
    }

    public String getConvictionCircumstances() {
        return convictionCircumstances;
    }

    public void setConvictionCircumstances(String convictionCircumstances) {
        this.convictionCircumstances = convictionCircumstances;
    }

    public String getConvictionDocuments() {
        return convictionDocuments;
    }

    public void setConvictionDocuments(String convictionDocuments) {
        this.convictionDocuments = convictionDocuments;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDirectorateId() {
        return directorateId;
    }

    public void setDirectorateId(String directorateId) {
        this.directorateId = directorateId;
    }

    public String getDismissErlier() {
        return dismissErlier;
    }

    public void setDismissErlier(String dismissErlier) {
        this.dismissErlier = dismissErlier;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDobCertImage() {
        return dobCertImage;
    }

    public void setDobCertImage(String dobCertImage) {
        this.dobCertImage = dobCertImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnrolledErlier() {
        return enrolledErlier;
    }

    public void setEnrolledErlier(String enrolledErlier) {
        this.enrolledErlier = enrolledErlier;
    }

    public String getEnrollmentYear() {
        return enrollmentYear;
    }

    public void setEnrollmentYear(String enrollmentYear) {
        this.enrollmentYear = enrollmentYear;
    }

    public String getExpDepartment() {
        return expDepartment;
    }

    public void setExpDepartment(String expDepartment) {
        this.expDepartment = expDepartment;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHqDgId() {
        return hqDgId;
    }

    public void setHqDgId(String hqDgId) {
        this.hqDgId = hqDgId;
    }

    public String getHqId() {
        return hqId;
    }

    public void setHqId(String hqId) {
        this.hqId = hqId;
    }

    public String getHqName() {
        return hqName;
    }

    public void setHqName(String hqName) {
        this.hqName = hqName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentificationMark1() {
        return identificationMark1;
    }

    public void setIdentificationMark1(String identificationMark1) {
        this.identificationMark1 = identificationMark1;
    }

    public String getIdentificationMark2() {
        return identificationMark2;
    }

    public void setIdentificationMark2(String identificationMark2) {
        this.identificationMark2 = identificationMark2;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getIndemnityBondImage() {
        return indemnityBondImage;
    }

    public void setIndemnityBondImage(String indemnityBondImage) {
        this.indemnityBondImage = indemnityBondImage;
    }

    public String getInsttDoc() {
        return insttDoc;
    }

    public void setInsttDoc(String insttDoc) {
        this.insttDoc = insttDoc;
    }

    public String getInsttId() {
        return insttId;
    }

    public void setInsttId(String insttId) {
        this.insttId = insttId;
    }

    public String getInsttName() {
        return insttName;
    }

    public void setInsttName(String insttName) {
        this.insttName = insttName;
    }

    public String getInsttType() {
        return insttType;
    }

    public void setInsttType(String insttType) {
        this.insttType = insttType;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getKinAddress() {
        return kinAddress;
    }

    public void setKinAddress(String kinAddress) {
        this.kinAddress = kinAddress;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public String getKinRelationship() {
        return kinRelationship;
    }

    public void setKinRelationship(String kinRelationship) {
        this.kinRelationship = kinRelationship;
    }

    public String getKinTelephone() {
        return kinTelephone;
    }

    public void setKinTelephone(String kinTelephone) {
        this.kinTelephone = kinTelephone;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getManagementType() {
        return managementType;
    }

    public void setManagementType(String managementType) {
        this.managementType = managementType;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getMedicalCertImage() {
        return medicalCertImage;
    }

    public void setMedicalCertImage(String medicalCertImage) {
        this.medicalCertImage = medicalCertImage;
    }

    public String getMemberRegimentFeeReceiptImage() {
        return memberRegimentFeeReceiptImage;
    }

    public void setMemberRegimentFeeReceiptImage(String memberRegimentFeeReceiptImage) {
        this.memberRegimentFeeReceiptImage = memberRegimentFeeReceiptImage;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getNCCEnrolledStudents() {
        return nCCEnrolledStudents;
    }

    public void setNCCEnrolledStudents(String nCCEnrolledStudents) {
        this.nCCEnrolledStudents = nCCEnrolledStudents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNccExp() {
        return nccExp;
    }

    public void setNccExp(String nccExp) {
        this.nccExp = nccExp;
    }

    public String getNccJointypeId() {
        return nccJointypeId;
    }

    public void setNccJointypeId(String nccJointypeId) {
        this.nccJointypeId = nccJointypeId;
    }

    public String getNccNo() {
        return nccNo;
    }

    public void setNccNo(String nccNo) {
        this.nccNo = nccNo;
    }

    public String getNccYear() {
        return nccYear;
    }

    public void setNccYear(String nccYear) {
        this.nccYear = nccYear;
    }

    public String getNearestPoliceStation() {
        return nearestPoliceStation;
    }

    public void setNearestPoliceStation(String nearestPoliceStation) {
        this.nearestPoliceStation = nearestPoliceStation;
    }

    public String getNearestRailwayStation() {
        return nearestRailwayStation;
    }

    public void setNearestRailwayStation(String nearestRailwayStation) {
        this.nearestRailwayStation = nearestRailwayStation;
    }

    public String getNocBySocietyDoc() {
        return nocBySocietyDoc;
    }

    public void setNocBySocietyDoc(String nocBySocietyDoc) {
        this.nocBySocietyDoc = nocBySocietyDoc;
    }

    public String getNominationFormImage() {
        return nominationFormImage;
    }

    public void setNominationFormImage(String nominationFormImage) {
        this.nominationFormImage = nominationFormImage;
    }

    public String getPanCardImage() {
        return panCardImage;
    }

    public void setPanCardImage(String panCardImage) {
        this.panCardImage = panCardImage;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getParadeStoreAvailable() {
        return paradeStoreAvailable;
    }

    public void setParadeStoreAvailable(String paradeStoreAvailable) {
        this.paradeStoreAvailable = paradeStoreAvailable;
    }

    public String getParentDecImage() {
        return parentDecImage;
    }

    public void setParentDecImage(String parentDecImage) {
        this.parentDecImage = parentDecImage;
    }

    public String getParentsAnnualIncome() {
        return parentsAnnualIncome;
    }

    public void setParentsAnnualIncome(String parentsAnnualIncome) {
        this.parentsAnnualIncome = parentsAnnualIncome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhysicalMailDate() {
        return physicalMailDate;
    }

    public void setPhysicalMailDate(String physicalMailDate) {
        this.physicalMailDate = physicalMailDate;
    }

    public String getPhysicalMailStatus() {
        return physicalMailStatus;
    }

    public void setPhysicalMailStatus(String physicalMailStatus) {
        this.physicalMailStatus = physicalMailStatus;
    }

    public String getPhysicalTestStatus() {
        return physicalTestStatus;
    }

    public void setPhysicalTestStatus(String physicalTestStatus) {
        this.physicalTestStatus = physicalTestStatus;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPrincipalAprovStatus() {
        return principalAprovStatus;
    }

    public void setPrincipalAprovStatus(String principalAprovStatus) {
        this.principalAprovStatus = principalAprovStatus;
    }

    public String getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getRegUsing() {
        return regUsing;
    }

    public void setRegUsing(String regUsing) {
        this.regUsing = regUsing;
    }

    public String getRegimentFeeReceiptImage() {
        return regimentFeeReceiptImage;
    }

    public void setRegimentFeeReceiptImage(String regimentFeeReceiptImage) {
        this.regimentFeeReceiptImage = regimentFeeReceiptImage;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getResultFor() {
        return resultFor;
    }

    public void setResultFor(String resultFor) {
        this.resultFor = resultFor;
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    public String getScRegNo() {
        return scRegNo;
    }

    public void setScRegNo(String scRegNo) {
        this.scRegNo = scRegNo;
    }

    public String getSchoolCollage() {
        return schoolCollage;
    }

    public void setSchoolCollage(String schoolCollage) {
        this.schoolCollage = schoolCollage;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getShortRangeAvailable() {
        return shortRangeAvailable;
    }

    public void setShortRangeAvailable(String shortRangeAvailable) {
        this.shortRangeAvailable = shortRangeAvailable;
    }

    public String getSignatureImage() {
        return signatureImage;
    }

    public void setSignatureImage(String signatureImage) {
        this.signatureImage = signatureImage;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getTeacherAvailable() {
        return teacherAvailable;
    }

    public void setTeacherAvailable(String teacherAvailable) {
        this.teacherAvailable = teacherAvailable;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(String totalStudent) {
        this.totalStudent = totalStudent;
    }

    public String getTotalVaccancy() {
        return totalVaccancy;
    }

    public void setTotalVaccancy(String totalVaccancy) {
        this.totalVaccancy = totalVaccancy;
    }

    public String getUniqueLoginId() {
        return uniqueLoginId;
    }

    public void setUniqueLoginId(String uniqueLoginId) {
        this.uniqueLoginId = uniqueLoginId;
    }

    public String getWillingToBeEnrolled() {
        return willingToBeEnrolled;
    }

    public void setWillingToBeEnrolled(String willingToBeEnrolled) {
        this.willingToBeEnrolled = willingToBeEnrolled;
    }

    public String getWing() {
        return wing;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public String getYearEstablishment() {
        return yearEstablishment;
    }

    public void setYearEstablishment(String yearEstablishment) {
        this.yearEstablishment = yearEstablishment;
    }


    private CadetDetailsModels(Builder builder) {
        expiryDate = builder.expiryDate;
        anoID = builder.anoID;
        address = builder.address;
        anoRecomStatus = builder.anoRecomStatus;
        bathroomAvailable = builder.bathroomAvailable;
        aadhaarNo = builder.aadhaarNo;
        accNo = builder.accNo;
        bathroomImages = builder.bathroomImages;
        adhaarCardImage = builder.adhaarCardImage;
        battalionName = builder.battalionName;
        city = builder.city;
        bankName = builder.bankName;
        mClass = builder.mClass;
        classId = builder.classId;
        battalionId = builder.battalionId;
        coAprovStatus = builder.coAprovStatus;
        bloodGroup = builder.bloodGroup;
        cadetId = builder.cadetId;
        collgSchoolType = builder.collgSchoolType;
        department = builder.department;
        directorateId = builder.directorateId;
        className = builder.className;
        dob = builder.dob;
        email = builder.email;
        convicted = builder.convicted;
        convictionCircumstances = builder.convictionCircumstances;
        convictionDocuments = builder.convictionDocuments;
        createdDate = builder.createdDate;
        fromDate = builder.fromDate;
        gender = builder.gender;
        dismissErlier = builder.dismissErlier;
        districtId = builder.districtId;
        districtName = builder.districtName;
        hqDgId = builder.hqDgId;
        dobCertImage = builder.dobCertImage;
        hqName = builder.hqName;
        enrolledErlier = builder.enrolledErlier;
        enrollmentYear = builder.enrollmentYear;
        expDepartment = builder.expDepartment;
        fatherName = builder.fatherName;
        id = builder.id;
        insttDoc = builder.insttDoc;
        insttName = builder.insttName;
        hqId = builder.hqId;
        insttType = builder.insttType;
        managementType = builder.managementType;
        identificationMark1 = builder.identificationMark1;
        identificationMark2 = builder.identificationMark2;
        ifscCode = builder.ifscCode;
        indemnityBondImage = builder.indemnityBondImage;
        nCCEnrolledStudents = builder.nCCEnrolledStudents;
        insttId = builder.insttId;
        nocBySocietyDoc = builder.nocBySocietyDoc;
        paradeStoreAvailable = builder.paradeStoreAvailable;
        isDeleted = builder.isDeleted;
        kinAddress = builder.kinAddress;
        kinName = builder.kinName;
        kinRelationship = builder.kinRelationship;
        kinTelephone = builder.kinTelephone;
        landmark = builder.landmark;
        location = builder.location;
        physicalMailDate = builder.physicalMailDate;
        maritalStatus = builder.maritalStatus;
        marks = builder.marks;
        medicalCertImage = builder.medicalCertImage;
        memberRegimentFeeReceiptImage = builder.memberRegimentFeeReceiptImage;
        mobile = builder.mobile;
        motherName = builder.motherName;
        physicalMailStatus = builder.physicalMailStatus;
        name = builder.name;
        nationality = builder.nationality;
        nccExp = builder.nccExp;
        nccJointypeId = builder.nccJointypeId;
        nccNo = builder.nccNo;
        nccYear = builder.nccYear;
        nearestPoliceStation = builder.nearestPoliceStation;
        nearestRailwayStation = builder.nearestRailwayStation;
        physicalTestStatus = builder.physicalTestStatus;
        nominationFormImage = builder.nominationFormImage;
        panCardImage = builder.panCardImage;
        panNo = builder.panNo;
        principalAprovStatus = builder.principalAprovStatus;
        parentDecImage = builder.parentDecImage;
        parentsAnnualIncome = builder.parentsAnnualIncome;
        password = builder.password;
        principalId = builder.principalId;
        shortRangeAvailable = builder.shortRangeAvailable;
        specialization = builder.specialization;
        pin = builder.pin;
        teacherAvailable = builder.teacherAvailable;
        toDate = builder.toDate;
        profileImage = builder.profileImage;
        regUsing = builder.regUsing;
        regimentFeeReceiptImage = builder.regimentFeeReceiptImage;
        registerId = builder.registerId;
        registrationNo = builder.registrationNo;
        resultFor = builder.resultFor;
        resultStatus = builder.resultStatus;
        scRegNo = builder.scRegNo;
        schoolCollage = builder.schoolCollage;
        schoolName = builder.schoolName;
        totalStudent = builder.totalStudent;
        signatureImage = builder.signatureImage;
        totalVaccancy = builder.totalVaccancy;
        stateId = builder.stateId;
        stateName = builder.stateName;
        status = builder.status;
        step = builder.step;
        stream = builder.stream;
        taluka = builder.taluka;
        yearEstablishment = builder.yearEstablishment;
        uniqueLoginId = builder.uniqueLoginId;
        willingToBeEnrolled = builder.willingToBeEnrolled;
        wing = builder.wing;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(CadetDetailsModels copy) {
        Builder builder = new Builder();
        builder.expiryDate = copy.getExpiryDate();
        builder.anoID = copy.getAnoID();
        builder.address = copy.getAddress();
        builder.anoRecomStatus = copy.getAnoRecomStatus();
        builder.bathroomAvailable = copy.getBathroomAvailable();
        builder.aadhaarNo = copy.getAadhaarNo();
        builder.accNo = copy.getAccNo();
        builder.bathroomImages = copy.getBathroomImages();
        builder.adhaarCardImage = copy.getAdhaarCardImage();
        builder.battalionName = copy.getBattalionName();
        builder.city = copy.getCity();
        builder.bankName = copy.getBankName();
        builder.mClass = copy.getmClass();
        builder.classId = copy.getClassId();
        builder.battalionId = copy.getBattalionId();
        builder.coAprovStatus = copy.getCoAprovStatus();
        builder.bloodGroup = copy.getBloodGroup();
        builder.cadetId = copy.getCadetId();
        builder.collgSchoolType = copy.getCollgSchoolType();
        builder.department = copy.getDepartment();
        builder.directorateId = copy.getDirectorateId();
        builder.className = copy.getClassName();
        builder.dob = copy.getDob();
        builder.email = copy.getEmail();
        builder.convicted = copy.getConvicted();
        builder.convictionCircumstances = copy.getConvictionCircumstances();
        builder.convictionDocuments = copy.getConvictionDocuments();
        builder.createdDate = copy.getCreatedDate();
        builder.fromDate = copy.getFromDate();
        builder.gender = copy.getGender();
        builder.dismissErlier = copy.getDismissErlier();
        builder.districtId = copy.getDistrictId();
        builder.districtName = copy.getDistrictName();
        builder.hqDgId = copy.getHqDgId();
        builder.dobCertImage = copy.getDobCertImage();
        builder.hqName = copy.getHqName();
        builder.enrolledErlier = copy.getEnrolledErlier();
        builder.enrollmentYear = copy.getEnrollmentYear();
        builder.expDepartment = copy.getExpDepartment();
        builder.fatherName = copy.getFatherName();
        builder.id = copy.getId();
        builder.insttDoc = copy.getInsttDoc();
        builder.insttName = copy.getInsttName();
        builder.hqId = copy.getHqId();
        builder.insttType = copy.getInsttType();
        builder.managementType = copy.getManagementType();
        builder.identificationMark1 = copy.getIdentificationMark1();
        builder.identificationMark2 = copy.getIdentificationMark2();
        builder.ifscCode = copy.getIfscCode();
        builder.indemnityBondImage = copy.getIndemnityBondImage();
        builder.nCCEnrolledStudents = copy.getNCCEnrolledStudents();
        builder.insttId = copy.getInsttId();
        builder.nocBySocietyDoc = copy.getNocBySocietyDoc();
        builder.paradeStoreAvailable = copy.getParadeStoreAvailable();
        builder.isDeleted = copy.getIsDeleted();
        builder.kinAddress = copy.getKinAddress();
        builder.kinName = copy.getKinName();
        builder.kinRelationship = copy.getKinRelationship();
        builder.kinTelephone = copy.getKinTelephone();
        builder.landmark = copy.getLandmark();
        builder.location = copy.getLocation();
        builder.physicalMailDate = copy.getPhysicalMailDate();
        builder.maritalStatus = copy.getMaritalStatus();
        builder.marks = copy.getMarks();
        builder.medicalCertImage = copy.getMedicalCertImage();
        builder.memberRegimentFeeReceiptImage = copy.getMemberRegimentFeeReceiptImage();
        builder.mobile = copy.getMobile();
        builder.motherName = copy.getMotherName();
        builder.physicalMailStatus = copy.getPhysicalMailStatus();
        builder.name = copy.getName();
        builder.nationality = copy.getNationality();
        builder.nccExp = copy.getNccExp();
        builder.nccJointypeId = copy.getNccJointypeId();
        builder.nccNo = copy.getNccNo();
        builder.nccYear = copy.getNccYear();
        builder.nearestPoliceStation = copy.getNearestPoliceStation();
        builder.nearestRailwayStation = copy.getNearestRailwayStation();
        builder.physicalTestStatus = copy.getPhysicalTestStatus();
        builder.nominationFormImage = copy.getNominationFormImage();
        builder.panCardImage = copy.getPanCardImage();
        builder.panNo = copy.getPanNo();
        builder.principalAprovStatus = copy.getPrincipalAprovStatus();
        builder.parentDecImage = copy.getParentDecImage();
        builder.parentsAnnualIncome = copy.getParentsAnnualIncome();
        builder.password = copy.getPassword();
        builder.principalId = copy.getPrincipalId();
        builder.shortRangeAvailable = copy.getShortRangeAvailable();
        builder.specialization = copy.getSpecialization();
        builder.pin = copy.getPin();
        builder.teacherAvailable = copy.getTeacherAvailable();
        builder.toDate = copy.getToDate();
        builder.profileImage = copy.getProfileImage();
        builder.regUsing = copy.getRegUsing();
        builder.regimentFeeReceiptImage = copy.getRegimentFeeReceiptImage();
        builder.registerId = copy.getRegisterId();
        builder.registrationNo = copy.getRegistrationNo();
        builder.resultFor = copy.getResultFor();
        builder.resultStatus = copy.getResultStatus();
        builder.scRegNo = copy.getScRegNo();
        builder.schoolCollage = copy.getSchoolCollage();
        builder.schoolName = copy.getSchoolName();
        builder.totalStudent = copy.getTotalStudent();
        builder.signatureImage = copy.getSignatureImage();
        builder.totalVaccancy = copy.getTotalVaccancy();
        builder.stateId = copy.getStateId();
        builder.stateName = copy.getStateName();
        builder.status = copy.getStatus();
        builder.step = copy.getStep();
        builder.stream = copy.getStream();
        builder.taluka = copy.getTaluka();
        builder.yearEstablishment = copy.getYearEstablishment();
        builder.uniqueLoginId = copy.getUniqueLoginId();
        builder.willingToBeEnrolled = copy.getWillingToBeEnrolled();
        builder.wing = copy.getWing();
        return builder;
    }

    public static final class Builder {
        private String expiryDate;
        private String anoID;
        private String address;
        private String anoRecomStatus;
        private String bathroomAvailable;
        private String aadhaarNo;
        private String accNo;
        private String bathroomImages;
        private String adhaarCardImage;
        private String battalionName;
        private String city;
        private String bankName;
        private String mClass;
        private String classId;
        private String battalionId;
        private String coAprovStatus;
        private String bloodGroup;
        private String cadetId;
        private String collgSchoolType;
        private String department;
        private String directorateId;
        private String className;
        private String dob;
        private String email;
        private String convicted;
        private String convictionCircumstances;
        private String convictionDocuments;
        private String createdDate;
        private String fromDate;
        private String gender;
        private String dismissErlier;
        private String districtId;
        private String districtName;
        private String hqDgId;
        private String dobCertImage;
        private String hqName;
        private String enrolledErlier;
        private String enrollmentYear;
        private String expDepartment;
        private String fatherName;
        private String id;
        private String insttDoc;
        private String insttName;
        private String hqId;
        private String insttType;
        private String managementType;
        private String identificationMark1;
        private String identificationMark2;
        private String ifscCode;
        private String indemnityBondImage;
        private String nCCEnrolledStudents;
        private String insttId;
        private String nocBySocietyDoc;
        private String paradeStoreAvailable;
        private String isDeleted;
        private String kinAddress;
        private String kinName;
        private String kinRelationship;
        private String kinTelephone;
        private String landmark;
        private String location;
        private String physicalMailDate;
        private String maritalStatus;
        private String marks;
        private String medicalCertImage;
        private String memberRegimentFeeReceiptImage;
        private String mobile;
        private String motherName;
        private String physicalMailStatus;
        private String name;
        private String nationality;
        private String nccExp;
        private String nccJointypeId;
        private String nccNo;
        private String nccYear;
        private String nearestPoliceStation;
        private String nearestRailwayStation;
        private String physicalTestStatus;
        private String nominationFormImage;
        private String panCardImage;
        private String panNo;
        private String principalAprovStatus;
        private String parentDecImage;
        private String parentsAnnualIncome;
        private String password;
        private String principalId;
        private String shortRangeAvailable;
        private String specialization;
        private String pin;
        private String teacherAvailable;
        private String toDate;
        private String profileImage;
        private String regUsing;
        private String regimentFeeReceiptImage;
        private String registerId;
        private String registrationNo;
        private String resultFor;
        private String resultStatus;
        private String scRegNo;
        private String schoolCollage;
        private String schoolName;
        private String totalStudent;
        private String signatureImage;
        private String totalVaccancy;
        private String stateId;
        private String stateName;
        private String status;
        private String step;
        private String stream;
        private String taluka;
        private String yearEstablishment;
        private String uniqueLoginId;
        private String willingToBeEnrolled;
        private String wing;

        private Builder() {
        }

        public Builder expiryDate(String val) {
            expiryDate = val;
            return this;
        }

        public Builder anoID(String val) {
            anoID = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder anoRecomStatus(String val) {
            anoRecomStatus = val;
            return this;
        }

        public Builder bathroomAvailable(String val) {
            bathroomAvailable = val;
            return this;
        }

        public Builder aadhaarNo(String val) {
            aadhaarNo = val;
            return this;
        }

        public Builder accNo(String val) {
            accNo = val;
            return this;
        }

        public Builder bathroomImages(String val) {
            bathroomImages = val;
            return this;
        }

        public Builder adhaarCardImage(String val) {
            adhaarCardImage = val;
            return this;
        }

        public Builder battalionName(String val) {
            battalionName = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder bankName(String val) {
            bankName = val;
            return this;
        }

        public Builder mClass(String val) {
            mClass = val;
            return this;
        }

        public Builder classId(String val) {
            classId = val;
            return this;
        }

        public Builder battalionId(String val) {
            battalionId = val;
            return this;
        }

        public Builder coAprovStatus(String val) {
            coAprovStatus = val;
            return this;
        }

        public Builder bloodGroup(String val) {
            bloodGroup = val;
            return this;
        }

        public Builder cadetId(String val) {
            cadetId = val;
            return this;
        }

        public Builder collgSchoolType(String val) {
            collgSchoolType = val;
            return this;
        }

        public Builder department(String val) {
            department = val;
            return this;
        }

        public Builder directorateId(String val) {
            directorateId = val;
            return this;
        }

        public Builder className(String val) {
            className = val;
            return this;
        }

        public Builder dob(String val) {
            dob = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder convicted(String val) {
            convicted = val;
            return this;
        }

        public Builder convictionCircumstances(String val) {
            convictionCircumstances = val;
            return this;
        }

        public Builder convictionDocuments(String val) {
            convictionDocuments = val;
            return this;
        }

        public Builder createdDate(String val) {
            createdDate = val;
            return this;
        }

        public Builder fromDate(String val) {
            fromDate = val;
            return this;
        }

        public Builder gender(String val) {
            gender = val;
            return this;
        }

        public Builder dismissErlier(String val) {
            dismissErlier = val;
            return this;
        }

        public Builder districtId(String val) {
            districtId = val;
            return this;
        }

        public Builder districtName(String val) {
            districtName = val;
            return this;
        }

        public Builder hqDgId(String val) {
            hqDgId = val;
            return this;
        }

        public Builder dobCertImage(String val) {
            dobCertImage = val;
            return this;
        }

        public Builder hqName(String val) {
            hqName = val;
            return this;
        }

        public Builder enrolledErlier(String val) {
            enrolledErlier = val;
            return this;
        }

        public Builder enrollmentYear(String val) {
            enrollmentYear = val;
            return this;
        }

        public Builder expDepartment(String val) {
            expDepartment = val;
            return this;
        }

        public Builder fatherName(String val) {
            fatherName = val;
            return this;
        }

        public Builder id(String val) {
            id = val;
            return this;
        }

        public Builder insttDoc(String val) {
            insttDoc = val;
            return this;
        }

        public Builder insttName(String val) {
            insttName = val;
            return this;
        }

        public Builder hqId(String val) {
            hqId = val;
            return this;
        }

        public Builder insttType(String val) {
            insttType = val;
            return this;
        }

        public Builder managementType(String val) {
            managementType = val;
            return this;
        }

        public Builder identificationMark1(String val) {
            identificationMark1 = val;
            return this;
        }

        public Builder identificationMark2(String val) {
            identificationMark2 = val;
            return this;
        }

        public Builder ifscCode(String val) {
            ifscCode = val;
            return this;
        }

        public Builder indemnityBondImage(String val) {
            indemnityBondImage = val;
            return this;
        }

        public Builder nCCEnrolledStudents(String val) {
            nCCEnrolledStudents = val;
            return this;
        }

        public Builder insttId(String val) {
            insttId = val;
            return this;
        }

        public Builder nocBySocietyDoc(String val) {
            nocBySocietyDoc = val;
            return this;
        }

        public Builder paradeStoreAvailable(String val) {
            paradeStoreAvailable = val;
            return this;
        }

        public Builder isDeleted(String val) {
            isDeleted = val;
            return this;
        }

        public Builder kinAddress(String val) {
            kinAddress = val;
            return this;
        }

        public Builder kinName(String val) {
            kinName = val;
            return this;
        }

        public Builder kinRelationship(String val) {
            kinRelationship = val;
            return this;
        }

        public Builder kinTelephone(String val) {
            kinTelephone = val;
            return this;
        }

        public Builder landmark(String val) {
            landmark = val;
            return this;
        }

        public Builder location(String val) {
            location = val;
            return this;
        }

        public Builder physicalMailDate(String val) {
            physicalMailDate = val;
            return this;
        }

        public Builder maritalStatus(String val) {
            maritalStatus = val;
            return this;
        }

        public Builder marks(String val) {
            marks = val;
            return this;
        }

        public Builder medicalCertImage(String val) {
            medicalCertImage = val;
            return this;
        }

        public Builder memberRegimentFeeReceiptImage(String val) {
            memberRegimentFeeReceiptImage = val;
            return this;
        }

        public Builder mobile(String val) {
            mobile = val;
            return this;
        }

        public Builder motherName(String val) {
            motherName = val;
            return this;
        }

        public Builder physicalMailStatus(String val) {
            physicalMailStatus = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder nationality(String val) {
            nationality = val;
            return this;
        }

        public Builder nccExp(String val) {
            nccExp = val;
            return this;
        }

        public Builder nccJointypeId(String val) {
            nccJointypeId = val;
            return this;
        }

        public Builder nccNo(String val) {
            nccNo = val;
            return this;
        }

        public Builder nccYear(String val) {
            nccYear = val;
            return this;
        }

        public Builder nearestPoliceStation(String val) {
            nearestPoliceStation = val;
            return this;
        }

        public Builder nearestRailwayStation(String val) {
            nearestRailwayStation = val;
            return this;
        }

        public Builder physicalTestStatus(String val) {
            physicalTestStatus = val;
            return this;
        }

        public Builder nominationFormImage(String val) {
            nominationFormImage = val;
            return this;
        }

        public Builder panCardImage(String val) {
            panCardImage = val;
            return this;
        }

        public Builder panNo(String val) {
            panNo = val;
            return this;
        }

        public Builder principalAprovStatus(String val) {
            principalAprovStatus = val;
            return this;
        }

        public Builder parentDecImage(String val) {
            parentDecImage = val;
            return this;
        }

        public Builder parentsAnnualIncome(String val) {
            parentsAnnualIncome = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder principalId(String val) {
            principalId = val;
            return this;
        }

        public Builder shortRangeAvailable(String val) {
            shortRangeAvailable = val;
            return this;
        }

        public Builder specialization(String val) {
            specialization = val;
            return this;
        }

        public Builder pin(String val) {
            pin = val;
            return this;
        }

        public Builder teacherAvailable(String val) {
            teacherAvailable = val;
            return this;
        }

        public Builder toDate(String val) {
            toDate = val;
            return this;
        }

        public Builder profileImage(String val) {
            profileImage = val;
            return this;
        }

        public Builder regUsing(String val) {
            regUsing = val;
            return this;
        }

        public Builder regimentFeeReceiptImage(String val) {
            regimentFeeReceiptImage = val;
            return this;
        }

        public Builder registerId(String val) {
            registerId = val;
            return this;
        }

        public Builder registrationNo(String val) {
            registrationNo = val;
            return this;
        }

        public Builder resultFor(String val) {
            resultFor = val;
            return this;
        }

        public Builder resultStatus(String val) {
            resultStatus = val;
            return this;
        }

        public Builder scRegNo(String val) {
            scRegNo = val;
            return this;
        }

        public Builder schoolCollage(String val) {
            schoolCollage = val;
            return this;
        }

        public Builder schoolName(String val) {
            schoolName = val;
            return this;
        }

        public Builder totalStudent(String val) {
            totalStudent = val;
            return this;
        }

        public Builder signatureImage(String val) {
            signatureImage = val;
            return this;
        }

        public Builder totalVaccancy(String val) {
            totalVaccancy = val;
            return this;
        }

        public Builder stateId(String val) {
            stateId = val;
            return this;
        }

        public Builder stateName(String val) {
            stateName = val;
            return this;
        }

        public Builder status(String val) {
            status = val;
            return this;
        }

        public Builder step(String val) {
            step = val;
            return this;
        }

        public Builder stream(String val) {
            stream = val;
            return this;
        }

        public Builder taluka(String val) {
            taluka = val;
            return this;
        }

        public Builder yearEstablishment(String val) {
            yearEstablishment = val;
            return this;
        }

        public Builder uniqueLoginId(String val) {
            uniqueLoginId = val;
            return this;
        }

        public Builder willingToBeEnrolled(String val) {
            willingToBeEnrolled = val;
            return this;
        }

        public Builder wing(String val) {
            wing = val;
            return this;
        }

        public CadetDetailsModels build() {
            return new CadetDetailsModels(this);
        }
    }

    public CadetDetailsModels() {
    }
}
