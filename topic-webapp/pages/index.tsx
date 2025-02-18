'use client'

import LoginForm from '@/app/ui/login-form';
import type { NextPage } from 'next';
import { useSession } from "next-auth/react";
import ListTopic from './topic';

const Home: NextPage = () => {
  const { data: session } = useSession();

  if (session == null) {
    return (<LoginForm />)
  } else {
    return (<ListTopic />)
  }
}

export default Home