for /l %%j in (1, 1, 4) do (
    for %%z in (0.8,0.9,0.95) do (
        for %%i in (100,200,500,750,1000,1500,2000,3000,5000,8124) do (
            echo %%j iteration for %%i
            java -jar -Xmx4028m -Xms4028m target/rock-1.0-SNAPSHOT-jar-with-dependencies.jar -m %%z dataSets/mushroom/agaricus-lepiota.data 20 %%i
        )
    )
)