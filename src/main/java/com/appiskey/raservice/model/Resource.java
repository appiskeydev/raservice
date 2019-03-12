package com.appiskey.raservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by khawar on 1/30/19.
 */

@EqualsAndHashCode(callSuper=true)
@Data
@Entity
@Table
public class Resource extends BaseModel{
//    private String resourceName;
    private String resourceCNIC;
    private String resourceDOB;
    private String resourceEmail;
    private String resourceAddress;
    private String resourcePhone;
    private String resourceReligion;
    private String resourceBloodGroup;
    private String resourceNationality;
    private String resourceEmergencyContactNo;
    private String resourceMaritalStatus;
    private String resourceResume;
    private Date resourceDateOfJoining;
    private int resourceWorkingDays;
    private float resourceExperience;
    private BigDecimal resourceSalaryPerMonth;
    private BigDecimal resourcePerHourRate;
    private String resourceShift;
    private String resourceBenefits;
    private String resourceContractType;     //isIntern, isPartTime , isFullTime



    @ManyToOne
    @JoinColumn(name="designation_id")
    private Designation resourceDesignation;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "resource_skill",
            joinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id",
                    referencedColumnName = "id"))
    private List<Skill> resourceSkills;



    @OneToMany(mappedBy = "project")
    private List<ResourceProject> resourceProject;



    private boolean resourcePartTime;


    @ManyToOne
    @JoinColumn(name="department_id")
    @NotFound(action= NotFoundAction.IGNORE)
    @JsonIgnore
    private Department resourceDepartment;


    @ManyToOne
    @JoinColumn(name = "reporting_id")
    @NotFound(action= NotFoundAction.IGNORE)
    protected Resource resourceReportingTo;
}
