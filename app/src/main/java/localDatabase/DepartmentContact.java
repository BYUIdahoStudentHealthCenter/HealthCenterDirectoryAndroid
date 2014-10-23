package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by jakobhartman on 10/23/14.
 */
@Table(name = "departmentContacts")
public class DepartmentContact extends HealthcenterModel {
    @Column(name = "department")
    public String department;
    @Column(name = "name")
    public String name;
    @Column (name = "number")
    public String number;

    public DepartmentContact(){
        super();
    }

    public List<DepartmentContact> getContacts(String departmentName){
        return new Select().from(DepartmentContact.class).where("department='" + departmentName +"'").execute();
    }

}
