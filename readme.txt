========== ========== ==========
  修行タイマー
========== ========== ==========

【 ソフト名 】修行タイマー
【 製 作 者 】谷本　樹希
【最終更新日】2021/1/22
【ファイル名】Sample.java
【同梱ファイル】readme.txt 音声ファイル 画像ファイル
【 E - mail 】t319059@m.chukyo-u.ac.jp

--------------------

◇ 概要 ◇
	修行タイマーは、自分の成長のために時間を使うためのタイマーアプリです。普通のタイマーとしても使用できます。

◇ 使用方法 ◇
	　起動するとウィンドウが表示されます。
	　上部にはタイマーの目的を設定するためのボタンがあります。そのボタンを押下してポップアップのテキスト入力欄に目的を入力してOKを押すと設定されます。
	　中央左には画像が表示されていて、右側にはタイマーの時間とスタートボタンとリセットボタンがあります。初期設定ではタイマーは30分に設定されているので、リセットボタンを押下して適宜変更してください。
	　スタートボタンを押すと、画像が回転を始めます。タイマーの時間がなくなるときに画像が一回転するようになっています。
	　ウィンドウ下部にはBGMボタンがあります。ボタンを押すと変更されます。止めるときにはBGMなしを選択してください。音声ファイルは、魔王魂の音源を利用しています。
	　タイマー終了時には、効果音がなります。
	　
◇ キーボードショートカット ◇
	start/stop Sキー or スペースキー
	reset Rキー

◇ プログラム構造 ◇
	画像を回転させるために、JPanelを継承して画像を表示するためのクラスを実装しました。[参考文献１]　アフィン変換を行って、回転させた画像を表示しています。
	AudioClipでは、長い音源は流せなかったのでAudioInputStreamとClipを使ってBGMの再生を実装しました。[参考文献２]　
	BGMを再生するときに直感的な操作が行えるようにラジオボタンを使用しました。[参考文献３]
	最初に画像を表示したときに画像が下にずれて表示されてしまう問題は直せませんでした。

◇ 参考文献 ◇
１．【Java】アフィン変換で画像などを回転させる
	https://nompor.com/2017/12/08/post-1695/ のんぽぐ 2021/1/22
２．【Java】WAVファイルの再生
	https://nompor.com/2017/12/14/post-128/ のんぽぐ 2021/1/22
３．ラジオボタンが選択か非選択かを取得する
	https://www.javadrive.jp/tutorial/jradiobutton/index15.html Let'sプログラミング 2021/1/22
