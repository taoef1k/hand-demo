import React, { useState } from 'react';
import {
  DownOutlined,
} from '@ant-design/icons';
import type { MenuProps } from 'antd';
import { Dropdown, Space } from 'antd';
import { useTranslation } from 'react-i18next'

const MenuLang: React.FC = () => {
  const { t, i18n } = useTranslation("global")

  const items: MenuProps['items'] = [
    {
      key: '1',
      label: (
        <a rel="noopener noreferrer" href="#" onClick={() => i18n.changeLanguage("en")}>
          English
        </a>
      ),
    },
    {
      key: '2',
      label: (
        <a rel="noopener noreferrer" href="#" onClick={() => i18n.changeLanguage("it")}>
          Italy
        </a>
      ),
    },
  ];

  return (
    <div style={{ width: 256 }}>
      <Dropdown menu={{ items }}>
        <a onClick={(e) => e.preventDefault()}>
          <Space>
            {t("header.chooseLanguage")}
            <DownOutlined />
          </Space>
        </a>
      </Dropdown>
    </div>
  );
};

export default MenuLang;