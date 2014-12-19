package localDatabase;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by chad on 10/23/14.
 */
@Table(name = "pictures")
public class Pictures extends HealthcenterModel {
    @Column(name = "name")
    public String name;
    @Column(name = "image")
    public String image;

    public Pictures(){
        super();
    }

    public List<Pictures>getPictures(){
        return new Select().from(Pictures.class).execute();
    }
    public Pictures getProfilePic(String picName){
        return new Select().from(Pictures.class).where("name = ?",picName).executeSingle();
    }


}
