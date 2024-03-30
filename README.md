# 概要
LINEヤフー株式会社（当時LINE株式会社）様が2021年の新卒採用試験として出題されたタクシーの運賃計算アプリのコーディングテストの問題[^1]をもとに、これまでJPIN[^2]で学んできたオブジェクト指向設計を実践してみた

簡単なフローチャートや概念モデリングの図とともに、この設計になった経緯やつまづきポイント、所感等を書き留めていく

[^1]:[LINEの新卒採用試験ズバリ問題解説 〜2021年開発コース(実装問題)版〜](https://engineering.linecorp.com/ja/blog/commentary-of-coding-test-2021)
[^2]:[JPIN – 就職・転職を決断する前に、人生の可能性を知ろう](https://www.jpin.info/)

## Version 1.0について
<img width="1210" alt="スクリーンショット 2024-03-30 20 36 07" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/ea5a696e-7e00-4fb4-bd7c-e7f1aa675262">
大雑把な処理の流れを書いてみたら結果これがmainメソッドのコードになった
　
<img width="1089" alt="スクリーンショット 2024-03-30 20 47 31" src="https://github.com/reo-fujimoto2653/taxi/assets/82273732/875ebd22-8698-43df-80a7-259e29ea359a">
「運賃はレコード、課金ルール、基本料金、時間帯による割増率によって決まる」ということを表現しようとした結果このような概念モデルになっている

「エリック・エヴァンスのドメイン駆動設計」[^3]を読んでいる中で「サービス」というものを知り、「初乗運賃」「距離運賃」「低速走行時間運賃」を計算するものはレコードの扱い方のルールを定めるだけで属性は持たなくていいだろうという考えからそれぞれの運賃を計算するサービスクラスを設けてみた
[^3]:[エリック・エヴァンスのドメイン駆動設計 著者:Eric Evans](https://www.amazon.co.jp/%E3%82%A8%E3%83%AA%E3%83%83%E3%82%AF%E3%83%BB%E3%82%A8%E3%83%B4%E3%82%A1%E3%83%B3%E3%82%B9%E3%81%AE%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88-Eric-Evans-ebook/dp/B00GRKD6XU/ref=sr_1_4?__mk_ja_JP=%E3%82%AB%E3%82%BF%E3%82%AB%E3%83%8A&crid=3EVGCL152U1J3&dib=eyJ2IjoiMSJ9.GC2YstolP1GEYBmLG4WPACNFOr34GrhRHTN3Hzra9CCYgnsGyBr8cE24IwwmSZ9e3sduvG-1hEnM5NKD2I4fuTFqe6WDawfhv8k-49a6BY5qfLdUgLYCPPVKMMccXwuEEHB-JMfd8nt09NyT7gl0Q5WnWIQ1XDkMmO5xmSSgF1gyILS4GFbJvFriq56OKU7FFf0HbLUEuBn5im32Ozzwfq03kVUb2rr4E9P-m86URTg.MPd2kt_bfckwwmQ43cDPhPey8NVEEct-OVBga7ViNTc&dib_tag=se&keywords=%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88&qid=1711804733&s=books&sprefix=%E3%83%89%E3%83%A1%E3%82%A4%E3%83%B3%E9%A7%86%E5%8B%95%E8%A8%AD%E8%A8%88%2Cstripbooks%2C196&sr=1-4)

ただし「初乗運賃」「距離運賃」「低速走行時間運賃」のそれぞれのルールの違いがここからはわからないので正しい概念モデリングとは言えないだろうという感じがしている

また低速走行時間運賃を計算する処理の中で総低速走行時間を求める際に、時刻と距離を持っているRecordを扱っているRecordListクラスの中で総低速走行時間を求めるメソッドを持たせているが、そのせいで「時速10km以下の時に低速走行時間メーターが加算される」というルールがRecordListクラスに定義されてしまっている

その他の気になりポイント
+ メソッド内に数値がベタ書きになっているのが気になっている（この段階ではそこまで考えなくていよい？）
  + よくよく見返すと動作確認のためだけに作った不要なメソッドが残されていた、、、
+ 処理内容がわかるようなメソッド名になるよう注意したが、引数の与え方には考慮の余地がまだまだありそう

### 今後やりたいこと
+ ビジネスルールを適切なクラスにまとめたい
+ 各種運賃計算の仕組みがわかる概念モデルに修正する
+ 安心してリファクタリングできるようにするためのテストコードを書く
+ パッケージ、モジュールシステムを使ったアクセス制限

