package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

/**
 * Created by chad on 11/3/14.
 */
@Table(name = "parent")
public class Parent extends HealthcenterModel {
    @Column(name = "parent_value", unique =  true, onUniqueConflict = Column.ConflictAction.IGNORE)
    public  String parent_value;

    public Parent(){
        super();
    }

    public Long Id(String p_value){
        return new Select().from(Parent.class).where("parent_value='" + p_value  +"'").executeSingle().getId();
    }


}
