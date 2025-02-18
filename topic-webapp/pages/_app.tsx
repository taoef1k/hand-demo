import React from 'react';
import type { AppProps } from 'next/app';
import { SessionProvider } from "next-auth/react"
import { ConfigProvider } from 'antd';
import theme from './theme/themeConfig';
import MenuLang from './menu-lang';
import { I18nextProvider } from 'react-i18next'
import i18next from 'i18next'

import global_en from '../locales/en/global.json'
import global_it from '../locales/it/global.json'

i18next.init({
  interpolation: { escapeValue: false },
  lng: 'auto',
  fallbackLng: 'en',
  resources: {
    en: {
      global: global_en,
    },
    it: {
      global: global_it,
    },
  },
})

const App = ({
  Component,
  pageProps: { session, ...pageProps }
}: AppProps) => (
  <SessionProvider session={session}>
    <ConfigProvider theme={theme}>
      <I18nextProvider i18n={i18next}>
        <MenuLang />
        <Component {...pageProps} />
      </I18nextProvider>
    </ConfigProvider>
  </SessionProvider>
);

export default App;