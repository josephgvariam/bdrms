package in.bigdash.rms.application.web.user;
import in.bigdash.rms.model.Role;
import in.bigdash.rms.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import io.springlets.web.NotFoundException;
import org.springframework.boot.jackson.JsonComponent;

/**
 * = RoleDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Role.class)
@JsonComponent
public class RoleDeserializer extends JsonObjectDeserializer<Role> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private RoleService roleService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param roleService
     * @param conversionService
     */
    @Autowired
    public RoleDeserializer(@Lazy RoleService roleService, ConversionService conversionService) {
        this.roleService = roleService;
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return RoleService
     */
    public RoleService getRoleService() {
        return roleService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param roleService
     */
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @return ConversionService
     */
    public ConversionService getConversionService() {
        return conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param conversionService
     */
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * TODO Auto-generated method documentation
     *
     * @param jsonParser
     * @param context
     * @param codec
     * @param tree
     * @return Role
     */
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
