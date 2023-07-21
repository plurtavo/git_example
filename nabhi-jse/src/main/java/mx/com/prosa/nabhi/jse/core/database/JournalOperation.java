package mx.com.prosa.nabhi.jse.core.database;

import mx.com.prosa.nabhi.misc.domain.complete.entity.JournalEntity;
import mx.com.prosa.nabhi.misc.domain.complete.repository.JournalRepository;
import mx.com.prosa.nabhi.misc.model.devices.constants.AtmEvent;
import mx.com.prosa.nabhi.misc.model.devices.constants.AtmResponse;
import mx.com.prosa.nabhi.misc.model.jdb.personalized.ATDTransactional;
import mx.com.prosa.nabhi.misc.model.jse.request.AtmNotificationModel;
import mx.com.prosa.nabhi.misc.model.jse.request.Generic;
import mx.com.prosa.nabhi.misc.util.IJournalOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.gonet.iso8583.constants.atm.FromAccount;
import us.gonet.iso8583.constants.atm.TranCodes;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@Component
public class JournalOperation implements IJournalOperation {

    private final JournalRepository journalRepository;

    @Autowired
    public JournalOperation( JournalRepository journalRepository ) {
        this.journalRepository = journalRepository;
    }

    @Override
    public void write( String terminalId, String message ) {
        JournalEntity entity = new JournalEntity( message, new Timestamp( new Date().getTime() ), terminalId );
        journalRepository.save( entity );
    }

    public void writeAcceptSurcharge( ATDTransactional atd, Generic generic, TranCodes tranCodes ) {
        FromAccount fromAccount = getAccount( generic.getTipoCuenta() );
        String id = MessageType.FROM_ACCOUNT.getKey() + fromAccount.getValue();
        findAndWrite( id, atd.getTerminalId() );
        id = MessageType.TRAN_CODE.getKey() + tranCodes.getValue();
        findAndWrite( id, atd.getTerminalId() );
        id = MessageType.SURCHARGE.getKey();
        findAndWrite( id, atd.getTerminalId(), generic.getTxCommission() );
    }

    public void waitAuth( ATDTransactional atd, TranCodes tranCodes ) {
        String id = MessageType.TRAN_CODE_WAIT.getKey() + tranCodes.getValue();
        findAndWrite( id, atd.getTerminalId() );
    }

    public void succesfulAuth( ATDTransactional atd, TranCodes tranCodes, String add ) {
        String id = MessageType.TRAN_CODE_SUCCESSFUL.getKey() + tranCodes.getValue();
        findAndWrite( id, atd.getTerminalId(), add );
    }

    private void findAndWrite( String id, String terminal, String... add ) {
        String additional = Arrays.toString( add );
        additional = additional.replace( "[", "" );
        additional = additional.replace( "]", "" );
        final String add2 = additional;
        String finalMessage = MessageJournal.valueOf( id ).message;
        write( terminal, finalMessage + add2 );
    }

    public void writeNotify( AtmNotificationModel model, AtmEvent atmEvent ) {
        write( model.getTermId(), atmEvent.getJournalMessage() );
    }

    public void writeResponse( AtmNotificationModel model, AtmResponse atmEvent ) {
        write( model.getTermId(), atmEvent.getMessage() );
    }


    private FromAccount getAccount( String value ) {
        for ( FromAccount account : FromAccount.values() ) {
            if ( account.getValue().equals( value ) ) {
                return account;
            }
        }
        return FromAccount.OTHER_ACCOUNT;
    }

    enum MessageType {
        FROM_ACCOUNT( "01" ),
        TRAN_CODE( "02" ),
        SURCHARGE( "03" ),
        TRAN_CODE_WAIT( "04" ),
        TRAN_CODE_SUCCESSFUL( "05" ),
        ;

        private final String key;

        MessageType( String key ) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    enum MessageJournal {

        M0110( "Cuenta de Ahorros seleccionada" ),
        M0120( "Cuenta de Cheques seleccionada" ),
        M0130( "Cuenta de Crédito seleccionada" ),
        M0201( "Retiro de Efectivo" ),
        M0202( "Pago de Servicios" ),
        M0231( "Consulta de Saldo" ),
        M0265( "Recarga de tiempo aire" ),
        M0294( "Consulta de Movimientos" ),
        M0296( "Cambio de NIP" ),
        M03( "El cliente acepta la comisión" ),
        M0401( "Esperando Autorización del retiro de efectivo" ),
        M0402( "Esperando Autorización de pago de servicios" ),
        M0431( "Esperando Autorización de la consulta de saldo" ),
        M0465( "Esperando Autorización de recarga de tiempo aire" ),
        M0494( "Esperando Autorización de la consulta de mov" ),
        M0496( "Esperando Autorización de cambio de NIP" ),
        M0501( "Retiro de efectivo autorizado, BufferAmount: " ),
        M0502( "Pago de Servicios Aprobado" ),
        M0531( "Consulta de saldo autorizada" ),
        M0565( "Recarga de tiempo aire autorizada" ),
        M0594( "Consulta de movimientos autorizada" ),
        M0596( "Cambio de NIP autorizado" ),

        ;
        private final String message;

        MessageJournal( String message ) {
            this.message = message;
        }
    }
}
