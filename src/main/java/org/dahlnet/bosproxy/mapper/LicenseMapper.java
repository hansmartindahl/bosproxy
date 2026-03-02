package org.dahlnet.bosproxy.mapper;

import org.dahlnet.bosproxy.dto.*;

import braiins.bos.v1.License;

public final class LicenseMapper {

    private LicenseMapper() {}

    public static GetLicenseStateResponseDTO toGetLicenseStateResponseDTO(License.GetLicenseStateResponse r) {
        NoneLicenseDTO none = null;
        ValidLicenseDTO valid = null;
        ExpiredLicenseDTO expired = null;
        switch (r.getStateCase()) {
            case NONE -> none = new NoneLicenseDTO(r.getNone().getTimeToRestricted());
            case LIMITED -> { /* limited has no fields */ }
            case VALID -> valid = toValidLicenseDTO(r.getValid());
            case EXPIRED -> expired = toExpiredLicenseDTO(r.getExpired());
            default -> {}
        }
        return new GetLicenseStateResponseDTO(none, r.getStateCase() == License.GetLicenseStateResponse.StateCase.LIMITED ? new Object() : null, valid, expired);
    }

    static ValidLicenseDTO toValidLicenseDTO(License.ValidLicense v) {
        int bsp = v.hasDevFee() ? v.getDevFee().getBsp() : 0;
        return new ValidLicenseDTO(v.getType().name(), v.getContractName(), v.getTimeToRestricted(), bsp);
    }

    static ExpiredLicenseDTO toExpiredLicenseDTO(License.ExpiredLicense e) {
        int bsp = e.hasDevFee() ? e.getDevFee().getBsp() : 0;
        return new ExpiredLicenseDTO(e.getType().name(), e.getContractName(), bsp);
    }
}
