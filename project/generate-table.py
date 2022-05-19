SIMULATION_LOG_LINES=9
logFile = open("./simulation.log", "r")
tableFile = open("./latex_table.txt", "w")
logLinesAsList = logFile.readlines()
logFile.close()
currentSequentialExecutionTime = 0
for i in range(0, len(logLinesAsList), SIMULATION_LOG_LINES):
    bodies = logLinesAsList[i + 2].translate({ord(i): None for i in "[BODIES] => \n"})
    steps = logLinesAsList[i + 3].translate({ord(i): None for i in "[STEPS] => \n"})
    threads = logLinesAsList[i + 4].translate({ord(i): None for i in "[THREADS] => \n"})
    executionTime = logLinesAsList[i + 5].translate({ord(i): None for i in "[EXECUTION_TIME_MS] => \n"})
    if int(threads) == 1: currentSequentialExecutionTime = executionTime
    tableFile.write(str(int(i / SIMULATION_LOG_LINES)) + " & " +
    str(bodies) + " & " +
    str(steps) + " & " +
    str(threads) + " & " +
    str(executionTime) + " & " +
    str(round(float(currentSequentialExecutionTime) / float(executionTime), 2)) + " \\\\ \n \hline\n")
tableFile.close()
