package in.bigdash.rms.service.impl;

import in.bigdash.rms.service.api.OtpService;
import in.bigdash.rms.service.impl.otp.HOTPAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OtpServiceImpl implements OtpService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public String generateOtp(Long requestId) {
        try {
            String pass = "BigDashRMS" + requestId;
            byte[] code = pass.getBytes();
            String otp = HOTPAlgorithm.generateOTP(code, 1, 6, false, 1);

            return otp;
        }catch(Exception e){
            log.error("Error generating OTP", e);
            return "123456";
        }
    }

    @Override
    public Boolean verifyOtp(String otp, Long requestId) {
        boolean result = generateOtp(requestId).equals(otp);
        return result;
    }
}
