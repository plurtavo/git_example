package mx.com.prosa.nabhi.misc.domain.complete.entity;

import com.google.gson.GsonBuilder;
import us.gonet.nabhi.misc.model.jdb.blob.ButtonsAllowed;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EntityListeners( AuditTable.class )
@Table( name = "TBL_SCREEN_GROUP" )
public class ScreenGroupEntity implements Serializable {

    private static final long serialVersionUID = 1111L;
    @Id
    @Column( length = 16, name = "PK_GROUP_ID" )
    private String groupId;

    @Column( length = 45, name = "PUBLICITY_NAME" )
    private String publicityName;

    @Column( length = 45, name = "BACK_GROUND" )
    private String backGround;

    @Lob
    @Column( name = "BUTTON_DEFAULT_STYLE" )
    private String buttonsStyle;

    @Lob
    @Column( name = "BODY_DEFAULT_STYLE" )
    private String bodyStyle;

    @Lob
    @Column( name = "BUTTONS_ALLOWED" )
    private ButtonsAllowed buttonsAllowed;

    @Column( length = 4, name = "FIID" )
    private String fiid;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId( String groupId ) {
        this.groupId = groupId;
    }

    public String getPublicityName() {
        return publicityName;
    }

    public void setPublicityName( String publicityName ) {
        this.publicityName = publicityName;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround( String backGround ) {
        this.backGround = backGround;
    }

    public String getButtonsStyle() {
        return buttonsStyle;
    }

    public void setButtonsStyle( String buttonsStyle ) {
        this.buttonsStyle = buttonsStyle;
    }

    public String getBodyStyle() {
        return bodyStyle;
    }

    public void setBodyStyle( String bodyStyle ) {
        this.bodyStyle = bodyStyle;
    }

    public ButtonsAllowed getButtonsAllowed() {
        return buttonsAllowed;
    }

    public void setButtonsAllowed( ButtonsAllowed buttonsAllowed ) {
        this.buttonsAllowed = buttonsAllowed;
    }

    public String getFiid() {
        return fiid;
    }

    public void setFiid( String fiid ) {
        this.fiid = fiid;
    }


    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
