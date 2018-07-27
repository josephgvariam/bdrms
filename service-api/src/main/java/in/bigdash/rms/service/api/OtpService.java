package in.bigdash.rms.service.api;

public interface OtpService {

    public abstract String generateOtp(Long requestId);

    public abstract Boolean verifyOtp(String otp, Long requestId);
}
