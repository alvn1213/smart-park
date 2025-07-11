<template>
  <div id="userLayout" :class="['user-layout-wrapper', isMobile && 'mobile']">
    <div class="container">
      <div class="login-form">
        <div class="top">
          <div class="header">
            <img src="~@/assets/logo.svg" class="logo" alt="logo">
            <span class="title">数字园区运营系统</span>
          </div>
        </div>
        <router-view />
      </div>

      <!-- <div class="top">
        <div class="header">
          <a href="/">
            <img src="~@/assets/logo.svg" class="logo" alt="logo">
            <span class="title">数字园区运营系统</span>
          </a>
        </div>
        <div class="desc">
        </div>
      </div> -->
      <div class="footer">
        <!-- <div class="links">
          <a href="">帮助</a>
          <a href="">隐私</a>
          <a href="">条款</a>
        </div> -->
        <!-- <div class="copyright">
          <a target="_blank" href="">沪ICP备2024XXXXXX号</a>
        </div> -->
      </div>
    </div>
  </div>
</template>

<script>
import { deviceMixin } from '@/store/device-mixin'

export default {
  name: 'UserLayout',
  mixins: [deviceMixin],
  mounted () {
    document.body.classList.add('userLayout')
  },
  beforeDestroy () {
    document.body.classList.remove('userLayout')
  }
}
</script>

<style lang="less" scoped>
  #userLayout.user-layout-wrapper {
    height: 100%;
    background: #f0f2f5 url(~@/assets/background.jpg) no-repeat 100%;
      background-size: 100%;
      background-size: initial;
      background-position: center center;
      background-repeat: no-repeat;
    &.mobile {
      .container {
        .main {
          max-width: 368px;
          width: 98%;
        }
      }
    }

    .container {
      width: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100%;
      .login-form{
        width: 40%;
        background-color: #f0f2f5 ;
        background-size: 100%;
        padding: 40px 0 20px;
        position: relative;
        .top {
          text-align: center;
          margin-bottom: 20px;
            .header {
              height: 44px;
              line-height: 44px;
          }
      }
      }
      a {
        text-decoration: none;
      }

      .top {
        text-align: center;

        .header {
          height: 44px;
          line-height: 44px;

          .badge {
            position: absolute;
            display: inline-block;
            line-height: 1;
            vertical-align: middle;
            margin-left: -12px;
            margin-top: -10px;
            opacity: 0.8;
          }

          .logo {
            height: 44px;
            vertical-align: top;
            margin-right: 16px;
            border-style: none;
          }

          .title {
            font-size: 33px;
            color: rgba(0, 0, 0, .85);
            font-family: Avenir, 'Helvetica Neue', Arial, Helvetica, sans-serif;
            font-weight: 600;
            position: relative;
            top: 2px;
          }
        }
        .desc {
          font-size: 14px;
          color: rgba(0, 0, 0, 0.45);
          margin-top: 12px;
          margin-bottom: 40px;
        }
      }

      .main {
        min-width: 260px;
        width: 368px;
        margin: 0 auto;
      }

      .footer {
        position: absolute;
        width: 100%;
        bottom: 0;
        padding: 0 16px;
        margin: 48px 0 24px;
        text-align: center;

        .links {
          margin-bottom: 8px;
          font-size: 14px;
          a {
            color: rgba(0, 0, 0, 0.45);
            transition: all 0.3s;
            &:not(:last-child) {
              margin-right: 40px;
            }
          }
        }
        .copyright {
          color: rgba(0, 0, 0, 0.45);
          font-size: 14px;
        }
      }
    }
  }
</style>
