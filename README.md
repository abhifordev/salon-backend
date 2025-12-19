## ⚠️ Known Issue (Intentional for Training)

Current implementation uses JWT for authentication but does NOT embed roles
inside the JWT. As a result:

- Tokens are role-agnostic
- RBAC checks rely indirectly on DB lookups
- A CUSTOMER token can access ADMIN endpoints

This is intentionally kept to demonstrate:
- Why stateless RBAC fails without role claims
- How to identify RBAC bugs
- How to fix RBAC correctly in Phase 3.2
