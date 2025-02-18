import NextAuth from "next-auth"
import CredentialsProvider from "next-auth/providers/credentials"

const credentialProvider = CredentialsProvider({
  name: 'Credentials',
  credentials: {
    username: { label: "Username", type: "text", placeholder: "jsmith" },
    password: { label: "Password", type: "password" }
  },
  async authorize(credentials, req) {
    const res = await fetch("http://localhost:3000/service/login", {
      method: 'POST',
      body: JSON.stringify(credentials),
      headers: { "Content-Type": "application/json" }
    })
    
    const user = await res.json();

    if (!res.ok) {
      throw new Error(user.message);
    }

    if (res.ok && user) {
      return user
    }
    
    return null
  }
});

export const authOptions = {
  providers: [credentialProvider],
  callbacks: {
    async jwt({ token, user }) {
      if (user) {
        token = { accessToken: user.accessToken }
      }
      return token;
    },
    async session({ token, session }) {
      session.accessToken = token.accessToken

      return session;
    },
  },
  pages: {
    signIn: "/",
    newUser: "/register"
  }
}

export default NextAuth(authOptions)