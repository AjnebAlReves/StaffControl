package net.shortninja.staffplus.server.compatibility.v1_7_R3;

import net.shortninja.staffplus.IStaffPlus;
import net.shortninja.staffplus.server.compatibility.IProtocol;
import net.shortninja.staffplus.server.compatibility.IProtocolProvider;

public class ProtocolProvider_v1_7_R3 implements IProtocolProvider {
    @Override
    public String getVersion() {
        return "v1_7_R3";
    }

    @Override
    public IProtocol create(IStaffPlus staffPlus) {
        return new Protocol_v1_7_R3(staffPlus);
    }
}
