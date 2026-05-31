package net.shortninja.staffplus.server.compatibility.v1_7_R4;

import net.shortninja.staffplus.IStaffPlus;
import net.shortninja.staffplus.server.compatibility.IProtocol;
import net.shortninja.staffplus.server.compatibility.IProtocolProvider;

public class ProtocolProvider_v1_7_R4 implements IProtocolProvider {
    @Override
    public String getVersion() {
        return "v1_7_R4";
    }

    @Override
    public IProtocol create(IStaffPlus staffPlus) {
        return new Protocol_v1_7_R4(staffPlus);
    }
}
