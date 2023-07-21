package mx.com.prosa.nabhi.misc.model.jdb;

import com.google.gson.GsonBuilder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import mx.com.prosa.nabhi.misc.domain.algorithm.dto.DispensedAlgorithm;
import mx.com.prosa.nabhi.misc.model.devices.DevicesWrapper;

import java.io.Serializable;
import java.util.List;

@ApiModel( description = "Json DTO representación de un ATD (Cajero)" )
public class ATD implements Serializable {

    private static final long serialVersionUID = 23453246534L;

    @ApiModelProperty( value = "Nemotécnico del cajero", example = "ABCCAP" )
    private String terminalId;
    @ApiModelProperty( value = "Número secuencia de transacción del cajero", example = "123" )
    private int sequenceNumber;
    @ApiModelProperty( value = "Número de licencia del cajero", example = "12345679812" )
    private String sequence;
    @ApiModelProperty( value = "Tipo de cajero", example = "NCR" )
    private String deviceType;
    @ApiModelProperty( value = "Modelo del cajero", example = "2700" )
    private String model;
    @ApiModelProperty( value = "Día de alta del cajero", example = "10/06/20" )
    private String postingDay;
    @ApiModelProperty( value = "Bandera que identifica si el cajero esta en línea", example = "true" )
    private boolean online;
    @ApiModelProperty( value = "IDF dueño del cajero", example = "B000" )
    private IDF idf;
    @ApiModelProperty( value = "Municipio/delegación donde se encuentra el cajero", example = "Coyoacan" )
    private County county;
    @ApiModelProperty( value = "IP del cajero", example = "192.168.100.2" )
    private String ip;
    @ApiModelProperty( value = "Cadena ISO de la última transacción", example = "ISO0160000500210B23062300003139375470464958812131=1703201000008030000000000000000499494100ABCCAP!" )
    private String lastTrx;
    @ApiModelProperty( value = "Bandera que indica si existe alguna transacción activa en el cajero", example = "false" )
    private boolean activeTrx;
    @ApiModelProperty( value = "Ubicación donde se encuentra el cajero", example = "Centro historico CDMX" )
    private String location;
    @ApiModelProperty( value = "Recibo generado para la última transacción", example =
            "FECHA         HORA     CAJERO          \n" +
            "24/06/21      01:38    ABCCAP          \n" +
            "PROS     \n" +
            "CUAUHTEMOC    DIF\n" +
            "\n" +
            "TARJETA:               547046XXXXXX2131\n" +
            "FOLIO:                           994941\n" +
            "RETIRO\n" +
            "TIPO CUENTA: CUENTA DE AHORROS                     \n" +
            "SALDO ANTERIOR:             $859,697.38\n" +
            "MONTO RETIRADO:                 $100.00\n" +
            "COMISION:                        $29.99\n" +
            "IVA:                              $5.71\n" +
            "TOTAL:                          $135.70\n" +
            "SALDO ACTUAL:               $859,561.69\n" +
            "AID:                     A0000000041010\n" +
            "ARQC:                  359D032AE0A95825\n" +
            "\n" +
            "\n" +
            "GRACIAS POR UTILIZAR LOS CAJEROS\n" +
            "VUELVA PRONTO\n" +
            "ATM CLOUD" )
    private String receipt;
    @ApiModelProperty( value = "Tipo de pantalla del cajero", example = "TOUCH" )
    private String screenType;
    @ApiModelProperty( value = "Grupo de pantallas", example = "B00_ATM_01" )
    private ScreenGroup screenGroup;
    @ApiModelProperty( value = "Nodo por el cual se conectará a la interfaz HISO", example = "S1A^TEST^ISO" )
    private NodeTCP nodeHiso;
    @ApiModelProperty( value = "Nodo por el cual se conectará para el traslado de pinblock", example = "S1A^TEST^JKE" )
    private NodeTCP nodeMtvk;
    @ApiModelProperty( value = "Dispositivos del cajero", example = "" )
    private DevicesWrapper terminalDevices;
    @ApiModelProperty( value = "Lista de registros almacenados en el Journal", example = "El cliente acepta la comision 35.70" )
    private List < Journal > journal;
    @ApiModelProperty( value = "Lista de registros de conexión del cajero", example = "14/06/21" )
    private List < UpTimes > upTimes;
    @ApiModelProperty( value = "Tipo de paquete transaccional para el cajero", example = "BASICO" )
    private Package packageName;
    //Cambio release/algoritmos
    @ApiModelProperty( value = "Tipo de algoritmo de dispensado", example = "UMBRALES" )
    private DispensedAlgorithm dispensedAlgorithm;
    //Cambio release/algoritmos

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId( String terminalId ) {
        this.terminalId = terminalId;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber( int sequenceNumber ) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence( String sequence ) {
        this.sequence = sequence;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType( String deviceType ) {
        this.deviceType = deviceType;
    }

    public String getPostingDay() {
        return postingDay;
    }

    public void setPostingDay( String postingDay ) {
        this.postingDay = postingDay;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline( boolean online ) {
        this.online = online;
    }

    public IDF getIdf() {
        return idf;
    }

    public void setIdf( IDF idf ) {
        this.idf = idf;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty( County county ) {
        this.county = county;
    }

    public String getIp() {
        return ip;
    }

    public void setIp( String ip ) {
        this.ip = ip;
    }

    public String getLastTrx() {
        return lastTrx;
    }

    public void setLastTrx( String lastTrx ) {
        this.lastTrx = lastTrx;
    }

    public boolean isActiveTrx() {
        return activeTrx;
    }

    public void setActiveTrx( boolean activeTrx ) {
        this.activeTrx = activeTrx;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt( String receipt ) {
        this.receipt = receipt;
    }

    public String getScreenType() {
        return screenType;
    }

    public void setScreenType( String screenType ) {
        this.screenType = screenType;
    }

    public ScreenGroup getScreenGroup() {
        return screenGroup;
    }

    public void setScreenGroup( ScreenGroup screenGroup ) {
        this.screenGroup = screenGroup;
    }

    public NodeTCP getNodeHiso() {
        return nodeHiso;
    }

    public void setNodeHiso( NodeTCP nodeHiso ) {
        this.nodeHiso = nodeHiso;
    }

    public NodeTCP getNodeMtvk() {
        return nodeMtvk;
    }

    public void setNodeMtvk( NodeTCP nodeMtvk ) {
        this.nodeMtvk = nodeMtvk;
    }

    public DevicesWrapper getTerminalDevices() {
        return terminalDevices;
    }

    public void setTerminalDevices( DevicesWrapper terminalDevices ) {
        this.terminalDevices = terminalDevices;
    }

    public List < Journal > getJournal() {
        return journal;
    }

    public void setJournal( List < Journal > journal ) {
        this.journal = journal;
    }

    public List < UpTimes > getUpTimes() {
        return upTimes;
    }

    public void setUpTimes( List < UpTimes > upTimes ) {
        this.upTimes = upTimes;
    }

    public String getModel() {
        return model;
    }

    public void setModel( String model ) {
        this.model = model;
    }

    public Package getPackageName() {
        return packageName;
    }

    public void setPackageName( Package packageName ) {
        this.packageName = packageName;
    }

    //Cambio release/algoritmos
    public DispensedAlgorithm getDispensedAlgorithm() {
        return dispensedAlgorithm;
    }

    public void setDispensedAlgorithm( DispensedAlgorithm dispensedAlgorithm ) {
        this.dispensedAlgorithm = dispensedAlgorithm;
    }
    //Cambio release/algoritmos

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson( this );
    }
}
