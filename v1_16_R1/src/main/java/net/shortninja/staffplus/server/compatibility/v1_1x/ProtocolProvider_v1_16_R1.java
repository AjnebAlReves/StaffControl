package net.shortninja.staffplus.server.compatibility.v1_1x;

import net.shortninja.staffplus.IStaffPlus;
import net.shortninja.staffplus.server.compatibility.IProtocol;
import net.shortninja.staffplus.server.compatibility.IProtocolProvider;

public class ProtocolProvider_v1_16_R1 implements IProtocolProvider {
    @Override
    public String getVersion() {
        return "v1_16_R1";
    }

    @Override
    public IProtocol create(IStaffPlus staffPlus) {
        return new Protocol_v1_16_R1(staffPlus);
    }
}
