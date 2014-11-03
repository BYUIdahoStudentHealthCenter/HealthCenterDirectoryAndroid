package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by chad on 11/3/14.
 */
@Table(name = "grandchild")
public class Grandchild extends HealthcenterModel {
    @Column(name = "grandchild_id")
    public  String grandchild_id;
    @Column(name = "child_id")
    public  String child_id;
    @Column(name = "grandchild_value")
    public  String grandchild_value;

    public Grandchild(){
        super();
    }

}
