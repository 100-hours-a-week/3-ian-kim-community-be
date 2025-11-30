package ktb3.full.community.security.access;

public interface PermissionService {

    boolean isOwner(Object targetId, Object principal);
}
