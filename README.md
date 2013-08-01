# このツールについて
SkyOnDemandやそのエンジンであるDataSpiderは、連携処理（スクリプト）をGUIで作成することが可能です。
しかしGUIであるがゆえに、異なるバージョンの変更点を比較することが容易ではありません。
このツールでは、スクリプトの定義ファイル（XML）を、一般的なテキスト比較ツール（diffやWinMerge）で比較できるようにすることを目標としています。

# 前提
コンパイルしたものをsod-transformer.jarとしてjar化して利用します。antでjarを生成するbuild.xmlがプロジェクトに含まれています。

# 利用手順
1. 　DataSpiderから比較したい2つのプロジェクトをダウンロードします。
2. ダウンロードしたディレクトリについてそれぞれ次のコマンドを実行します。
    java -jar sod-transformer.jar プロジェクトのディレクトリ
3. diffやWinMergeを使って、拡張子.comparableのファイルを比較します。
