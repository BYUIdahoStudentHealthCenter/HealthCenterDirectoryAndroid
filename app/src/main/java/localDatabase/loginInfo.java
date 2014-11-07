package localDatabase;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

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

    public loginInfo() {super();}

}
