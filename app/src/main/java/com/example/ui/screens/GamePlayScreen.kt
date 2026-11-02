package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.components.RecomecaTopBar
import com.example.ui.screens.games.*
import com.example.ui.theme.DarkBackground

@Composable
fun GamePlayScreen(
    gameId: String,
    onBack: () -> Unit,
    onRecordScore: (gameId: String, gameName: String, score: Int, moves: Int, duration: Int, difficulty: String) -> Unit
) {
    val (title, content) = when (gameId) {
        "memory" -> Pair("Jogo da Memória", @Composable {
            MemoryGame(onGameComplete = { score, moves, duration ->
                onRecordScore("memory", "Jogo da Memória", score, moves, duration, "Normal")
            })
        })
        "sudoku" -> Pair("Sudoku 9x9", @Composable {
            SudokuGame(onGameComplete = { score, moves, duration ->
                onRecordScore("sudoku", "Sudoku 9x9", score, moves, duration, "Médio")
            })
        })
        "wordsearch" -> Pair("Caça-Palavras da Superação", @Composable {
            WordSearchGame(onGameComplete = { score, moves, duration ->
                onRecordScore("wordsearch", "Caça-Palavras", score, moves, duration, "Normal")
            })
        })
        "quiz" -> Pair("Quiz de Conhecimentos", @Composable {
            QuizGame(onGameComplete = { score, moves, duration ->
                onRecordScore("quiz", "Quiz Geral", score, moves, duration, "Geral")
            })
        })
        "logic" -> Pair("Desafios de Lógica", @Composable {
            LogicPuzzleGame(onGameComplete = { score, moves, duration ->
                onRecordScore("logic", "Desafios Lógicos", score, moves, duration, "Médio")
            })
        })
        "sliding" -> Pair("Quebra-Cabeça Deslizante", @Composable {
            SlidingPuzzleGame(onGameComplete = { score, moves, duration ->
                onRecordScore("sliding", "Sliding Puzzle", score, moves, duration, "3x3")
            })
        })
        "reaction" -> Pair("Treino de Reação & Atenção", @Composable {
            ReactionGame(onGameComplete = { score, moves, duration ->
                onRecordScore("reaction", "Treino de Reação", score, moves, duration, "Reflexos")
            })
        })
        "scramble" -> Pair("Palavras Embaralhadas", @Composable {
            ScrambleGame(onGameComplete = { score, moves, duration ->
                onRecordScore("scramble", "Palavras Embaralhadas", score, moves, duration, "Normal")
            })
        })
        "math" -> Pair("Desafio Mental Matemático", @Composable {
            MathChallengeGame(onGameComplete = { score, moves, duration ->
                onRecordScore("math", "Desafio Matemático", score, moves, duration, "Rápido")
            })
        })
        "breathing" -> Pair("Respiração Guiada", @Composable {
            BreathingExercise(onGameComplete = { score, moves, duration ->
                onRecordScore("breathing", "Respiração 4-7-8", score, moves, duration, "Relax")
            })
        })
        else -> Pair("Jogo", @Composable {
            MemoryGame(onGameComplete = { score, moves, duration ->
                onRecordScore("memory", "Jogo da Memória", score, moves, duration, "Normal")
            })
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = title,
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            content()
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
