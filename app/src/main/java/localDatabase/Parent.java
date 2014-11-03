package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by chad on 11/3/14.
 */
@Table(name = "parent")
public class Parent extends HealthcenterModel {
    @Column(name = "parent_id")
    public  String parent_id;
    @Column(name = "parent_value")
    public  String parent_value;

}
