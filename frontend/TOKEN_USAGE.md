# Token Authentication via URL

## Overview
The BeeThere frontend now supports loading JWT tokens from URL query parameters, making it easy to authenticate users by providing a token in the URL.

## Usage

### Method 1: URL Query Parameter (Recommended)
Simply append the token as a query parameter when loading the application:

```
http://localhost:3000/?token=YOUR_JWT_TOKEN_HERE
```

**Full Example:**
```
http://localhost:3000/?token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJHaWxtb3VyIiwibG9jYXRpb24iOiJHZXJtYW55IiwiaWQiOjQ2LCJkZXBhcnRtZW50IjoiU2FsZXMiLCJ0aXRsZSI6IlNhbGVzIEFnZW50IiwiZmlyc3RfbmFtZSI6IkFndXN0aW4iLCJzdWIiOiJBZ3VzdGluIEdpbG1vdXIiLCJpYXQiOjE3NjU0ODc5MjgsImV4cCI6MTc2NTQ5MTUyOH0.wjp2UvXHp0TPedxJkY6wyVdzi_7YdRgcdhIHkHGkNVc
```

### Method 2: Default Token
If no token is provided in the URL, the application will use a hardcoded default token (Agustin Gilmour's credentials).

### Method 3: Programmatic Token Update
You can manually update the token in code:

```typescript
import { useAuthStore } from '@/stores/auth-store'

const authStore = useAuthStore()
authStore.setToken('YOUR_NEW_TOKEN_HERE')
```

## How It Works

1. **On App Load**: The auth store checks for a `token` query parameter in the URL
2. **Token Storage**: If found, it uses that token; otherwise, it uses the default
3. **Identity Loading**: The app automatically calls `loadIdentity()` which fetches employee details from the backend using the token
4. **API Requests**: All authenticated API requests use the stored token

## Implementation Details

The token initialization happens in `/frontend/src/stores/auth-store.ts`:

```typescript
const initializeToken = () => {
    const urlParams = new URLSearchParams(window.location.search);
    const tokenParam = urlParams.get('token');
    
    if (tokenParam) {
        console.log('Token loaded from URL parameter');
        return tokenParam;
    }
    
    // Default token if none provided in URL
    console.log('Using default token');
    return 'eyJhbGciOiJIUzI1NiJ9...';
};
```

## Examples for Different Users

### Employee: Margit Filipczynski (ID: 19)
```
http://localhost:3000/?token=<Margit's JWT token>
```

### Employee: Agustin Gilmour (ID: 46) - Default
```
http://localhost:3000/
```
or
```
http://localhost:3000/?token=eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoIFNlcnZpY2UiLCJsYXN0X25hbWUiOiJHaWxtb3VyIiwibG9jYXRpb24iOiJHZXJtYW55IiwiaWQiOjQ2LCJkZXBhcnRtZW50IjoiU2FsZXMiLCJ0aXRsZSI6IlNhbGVzIEFnZW50IiwiZmlyc3RfbmFtZSI6IkFndXN0aW4iLCJzdWIiOiJBZ3VzdGluIEdpbG1vdXIiLCJpYXQiOjE3NjU0ODc5MjgsImV4cCI6MTc2NTQ5MTUyOH0.wjp2UvXHp0TPedxJkY6wyVdzi_7YdRgcdhIHkHGkNVc
```

## Security Notes

⚠️ **Important Security Considerations:**

1. **HTTPS Only in Production**: Always use HTTPS in production to prevent token interception
2. **Token Expiration**: Tokens have an expiration time (`exp` claim in JWT)
3. **URL Logging**: Be aware that URL parameters may be logged in browser history and server logs
4. **Consider Alternative Methods**: For production, consider more secure authentication flows (OAuth, session cookies, etc.)

## Debugging

Check the browser console for token loading messages:
- `"Token loaded from URL parameter"` - Token successfully loaded from URL
- `"Using default token"` - No token in URL, using default
- `"Token manually updated"` - Token was set programmatically

## Testing Different Users

To test the application with different employee identities:

1. Get a JWT token from your backend auth service for the desired employee
2. Append it to the URL: `http://localhost:3000/?token=PASTE_TOKEN_HERE`
3. The app will automatically load that employee's identity and display their data

## Integration with Backend

The token is sent to the backend in the `Bearer` header:

```typescript
const response = await axios.get('http://localhost:8080/employees/employee', {
    headers: {
        'Bearer': token.value
    }
});
```

Make sure your backend is configured to accept this header format.
