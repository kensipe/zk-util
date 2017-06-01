# zk-util
zookeeper utility using ammonite


## prerequisites

* Java 8
* Scala 2.12
* Ammonite 0.9.5

Installing Ammonite

* Mac - brew install ammonite-repl
* curl - curl -Ls -o /bin/amm https://github.com/lihaoyi/Ammonite/releases/download/0.9.5/2.10-0.9.5 && chmod +x /bin/amm

## Description

The [zk.sc](zk.sc) file provides the functions for accessing a local zk cluster.  It assumes "localhost:2181", changes to that can be made to the script.   The script imports the [twitter util](https://github.com/twitter/util). Specifically the util-zk package.  It provides 3 functions; `get`, `set`, `setId`.

* `get` - provides the string value of the path provided
* `set` - will set the string value of the path
* `setId` - will add protobuf delimiters to what is expected to be a mesos framework id.

The reason for this script, is the `zkCli` client does allow the setting of special characters to a znode (among of limitations).   The `0x0a(` is needed as the current prefix to the frameworkID.  This is the prefix for Marathon 1.4.3 (and should NOT be used to set previous frameworkIDs).

It is also expected that you have access and auth to zk.  
There is No error handling in this script!

You will need to `chmod +x zk.sc`

## Running the script

getting the value: `./zk.sc get /marathon/state/framework-id/b/id`

setting the frameworkID:  `./zk.sc setId /marathon/state/framework-id/b/id d8bcce4a-3570-4f65-8c19-35a793e5337e-0001`

**note**: the first run compiles and may not invoke an action.
