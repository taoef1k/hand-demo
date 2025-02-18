import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: '/service/:path*',
        destination: 'http://localhost:9000/:path*' // Proxy to Backend
      }
    ]
  }
};

export default nextConfig;
