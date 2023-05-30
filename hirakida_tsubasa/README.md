# Photo Map

### 使用技術
- HTML
- CSS
    - Tailwind CSS
    - daisyUI
- JavaScript
    - jQuery 3.6.0  
- java 17
    - Spring 3.0.6  
- Postgres 15

### H2を使い、自分のマシン上で動作させる方法
下記の2つの手順が必要です。
1. build.gradleファイルでインストールしているDB用のドライバーをH2のドライバーに変更する
以下を削除
```
runtimeOnly 'org.postgresql:postgresql'
```
以下を追加
```
implementation 'com.h2database:h2:1.4.200'
```
2. application.propetiesのDB設定をH2用に変更する
以下で上書き
```
spring.datasource.driver-class-name=org.h2.Driver

spring.datasource.url=jdbc:h2:mem:testdb

spring.datasource.username=sa

spring.datasource.password=

spring.h2.console.enabled=true
```
※データを永続的に保存したい場合は、別途Postgresをインストールしてください。

### 使用している主なライブラリ
`spring-boot-starter-jdbc`
・・・jdbcを使うためのライブラリ
`thymeleaf-layout-dialect`
・・・レイアウトを分割するために使用したライブラリ(本アプリは正しい使い方ができていないので、注意してください)
`spring-boot-starter-security`
・・・Spring Security。認証認可のためのライブラリ
`thymeleaf-extras-springsecurity`
・・・ThymeleafとSpringSecurityを連携させるためのライブラリ

## 概要
画像と位置情報を投稿できるサービスです。

### 機能
- ログイン/ログアウト機能
- 投稿機能(画像投稿)
- いいね機能(ajax機能有)
- コメント機能
- 投稿された場所をクリックするとGoogle Mapへリンクする

### 工夫したところ
- 画像が投稿できること
    - 苦労したことは、投稿後画像を反映させるためにリダイレクトが2回必要なバグが発生したこと。
　バグ理由は、一時保管するディレクトリの作成及び画像の移動処理が一覧ページのリダイレクトまでに。間に合わなかったことだった。
　解決策としては、「Thread.sleep()」でリダイレクトまでの時間稼ぎをするという、一時的な解決策をとった。
- ハートマークの大きさや色
    - ハートマーク自体はfontawesomeを使用。
  色やサイズはcssで編集している。
- 投稿された場所をクリックするとGoogle Mapへリンクする
    - `https://www.google.com/maps/search/?api=1&query={場所}`で任意の場所でGoogle Mapへリンクできる。
- 自分の投稿であるかやログインしていないかで、表示させるものの出しわけ
    - thymeleaf-extras-springsecurityの機能を使用。
　ログインしていないユーザーに表示させたいものは、`sec:authorize="!authenticated`の記述がいる。
　ログインしているユーザーに表示させたいものは、`sec:authorize="authenticated`の記述がいる。

### 改善させたいこと・反省点
- Spring Securityについて理解していないことが多いが、実装できてしまっていること
- 現状採用しているフォントの`Rampart One`がForm内では読みづらい
- TimeStampがない