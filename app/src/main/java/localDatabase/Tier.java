package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by chad on 11/6/14.
 */
@Table(name = "Tier")
public class Tier extends HealthcenterModel{
    @Column(name="parent")
    public String parent;
    @Column(name="child")
    public String child;

    public Tier(){ super();}



}
