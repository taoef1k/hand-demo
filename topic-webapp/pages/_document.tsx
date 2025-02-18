import React from 'react';
import { createCache, extractStyle, StyleProvider } from '@ant-design/cssinjs';
import Document, { Head, Html, Main, NextScript } from 'next/document';
import type { DocumentContext } from 'next/document';
import { Layout } from 'antd';
const { Content } = Layout;

const bodyStyle = {
  // backgroundColor: '#b8cff5',
  // textAlign: 'center'
}

const layoutStyle = {
  overflow: 'hidden',
  width: 'calc(100% - 0px)',
  maxWidth: 'calc(100% - 0px)'
};

const contentStyle: React.CSSProperties = {
  color: '#fff',
};

const MyDocument = () => (
  <Html lang="en">
    <Head />
    <body style={bodyStyle}>
      <Layout style={layoutStyle}>
        <Content style={contentStyle}>
          <Main />
          <NextScript />
        </Content>
      </Layout>
    </body>
  </Html>
);

MyDocument.getInitialProps = async (ctx: DocumentContext) => {
  const cache = createCache();
  const originalRenderPage = ctx.renderPage;
  ctx.renderPage = () =>
    originalRenderPage({
      enhanceApp: (App) => (props) => (
        <StyleProvider cache={cache}>
          <App {...props} />
        </StyleProvider>
      ),
    });

  const initialProps = await Document.getInitialProps(ctx);
  const style = extractStyle(cache, true);
  return {
    ...initialProps,
    styles: (
      <>
        {initialProps.styles}
        <style dangerouslySetInnerHTML={{ __html: style }} />
      </>
    ),
  };
};

export default MyDocument;