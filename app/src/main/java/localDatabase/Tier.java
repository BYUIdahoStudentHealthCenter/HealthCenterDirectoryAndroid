package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

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

    public List<Tier> getAll() {
        return new Select().from(Tier.class).execute();
    }

    public List<Tier> getChild(String parent) {
        return new Select().from(Tier.class).where("parent = ?",parent).execute();
    }

}
