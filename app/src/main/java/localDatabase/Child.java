package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by chad on 11/3/14.
 */
@Table(name = "child")
public class Child extends HealthcenterModel {
    @Column(name = "child_id")
    public String child_id;
    @Column(name = "parent_id")
    public  String parent_id;
    @Column(name = "parent_value")
    public  String child_value;

}
