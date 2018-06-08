package in.bigdash.rms.application.web.role;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.service.api.RoleService;
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
public class RoleDeserializer extends JsonObjectDeserializer<Role> {


    private RoleService roleService;


    private ConversionService conversionService;


    @Autowired
    public RoleDeserializer(@Lazy RoleService roleService, ConversionService conversionService) {
        this.roleService = roleService;
        this.conversionService = conversionService;
    }


    public RoleService getRoleService() {
        return roleService;
    }


    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    public ConversionService getConversionService() {
        return conversionService;
    }


    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public Role deserializeObject(JsonParser jsonParser, DeserializationContext context, ObjectCodec codec, JsonNode tree) {
        String idText = tree.asText();
        Long id = conversionService.convert(idText, Long.class);
        Role role = roleService.findOne(id);
        if (role == null) {
            throw new NotFoundException("Role not found");
        }
        return role;
    }
}
