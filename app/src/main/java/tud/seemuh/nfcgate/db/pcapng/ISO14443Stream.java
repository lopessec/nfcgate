package tud.seemuh.nfcgate.db.pcapng;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import tud.seemuh.nfcgate.db.pcapng.base.PcapPacket;
import tud.seemuh.nfcgate.db.pcapng.base.PcapStream;
import tud.seemuh.nfcgate.util.NfcComm;

public class ISO14443Stream extends PcapStream {
    private final static short LINKTYPE = 264;

    public ISO14443Stream() {
        super(LINKTYPE);
    }

    public ISO14443Stream append(List<NfcComm> comms) {
        for (NfcComm comm : comms)
            append(new ISO14443Packet(comm));

        return this;
    }

    public List<NfcComm> readAll(InputStream in) throws IOException {
        super.read(in);

        // convert packet list to NfcComm list
        List<NfcComm> result = new ArrayList<>();
        for (PcapPacket packet : getPackets())
            result.add(((ISO14443Packet) packet).getData());
        return result;
    }

    @Override
    protected PcapPacket readPacket(DataInputStream in) throws IOException {
        return new ISO14443Packet().read(in);
    }
}
