package in.bigdash.rms.application.web.api.user;
import in.bigdash.rms.model.User;
import in.bigdash.rms.service.api.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.springlets.web.NotFoundException;
import org.springframework.boot.jackson.JsonComponent;


@JsonComponent
public class UserDeserializer extends JsonObjectDeserializer<User> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private UserService userService;


    private ConversionService conversionService;


    @Autowired
    public UserDeserializer(@Lazy UserService userService, ConversionService conversionService) {
        this.userService = userService;
        this.conversionService = conversionService;
    }


    public UserService getUserService() {
        return userService;
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public User deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {

        if(tree.get("id")==null){
            return null;
        }

        String idText = tree.get("id").asText();

        if(StringUtils.isBlank(idText) || idText.equals("null")){
            return null;
        }

        Long id = conversionService.convert(idText, Long.class);
        User user = userService.findOne(id);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }
}
