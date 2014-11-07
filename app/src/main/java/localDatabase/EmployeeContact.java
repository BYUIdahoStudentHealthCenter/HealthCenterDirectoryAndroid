package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by jakobhartman on 10/28/14.
 */
@Table(name = "employeeContact")
public class EmployeeContact extends HealthcenterModel {

    @Column(name = "department")
    public  String department;
    @Column(name = "departmentEmail")
    public String departmentEmail;
    @Column(name = "departmentNumber")
    public String departmentNumber;
    @Column(name = "firstName")
    public String firstName;
    @Column(name = "lastName")
    public String lastName;
    @Column(name = "imageName")
    public String imageName;
    @Column(name = "personalEmail")
    public String personalEmail;
    @Column (name = "phoneNumber")
    public String phoneNumber;
    @Column (name = "position")
    public String position;
    @Column (name = "status")
    public String status;
    @Column (name = "tier")
    public String tier;

    public EmployeeContact(){
        super();
    }

    public List<EmployeeContact> getContactsByPosition(String name){
        return new Select().from(EmployeeContact.class).where("position='" + name +"'").execute();
    }

    public List<EmployeeContact> getContactsByTier(String name){
        return new Select().from(EmployeeContact.class).where("tier='" + name +"'").execute();
    }

    public List<EmployeeContact> getContactsByStatus(String name){
        return new Select().from(EmployeeContact.class).where("status='" + name +"'").execute();
    }

    public  List<EmployeeContact>getContactsByFirstName(String name){
        return new Select().from(EmployeeContact.class).where("firstName='" + name +"'").execute();
    }

    public List<EmployeeContact>getContactsByDepartment(String name){
        return new Select().from(EmployeeContact.class).where("department='" + name + "'").execute();
    }

    public List <EmployeeContact>getTier(String email){
        return new Select().from(EmployeeContact.class).where("personalEmail='"+ email + "'").execute();
    }

    public List<EmployeeContact> getAllEmployees(){
        return new Select().from(EmployeeContact.class).execute();
    }



}
