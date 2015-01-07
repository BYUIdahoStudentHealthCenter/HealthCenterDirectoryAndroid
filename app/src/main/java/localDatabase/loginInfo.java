package localDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jakobhartman on 10/29/14.
 */
@Table (name = "loginInfo")
public class loginInfo extends Model{
    @Column(name = "username")
    public String username;
    @Column(name = "password")
    public String password;
    @Column(name = "lastLogIn")
    public Calendar lastLogIn; // lastSync will actually the expiration date

    public loginInfo() {
        super();
    }

    public List<loginInfo> getAll(){
        return new Select().from(loginInfo.class).execute();
    }

    public loginInfo getLastLogIn(String user) {
        return new Select().from(loginInfo.class).where("username = ?",user).executeSingle();
    }

}
