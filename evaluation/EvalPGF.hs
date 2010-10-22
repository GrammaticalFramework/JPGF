{- This little programm, when given a pgf file and a language name,
   reads an input on stdin and tries to parse it and then relinearize it in
   in the given language.
   The only purpose of it is to be able to evaluate the runtime performances
   to have a comparaison point for the performances of the Java runtime.
-}
module Main where

import System.Environment (getArgs)
import System.IO (stderr, hPutStrLn)
import PGF
import Data.Maybe (fromJust)

split_argument :: [String] -> Either String (String,String)
split_argument [a] = Right (pgf,language)
   where pgf = takeWhile (/=':') a
         language = drop 1 $ dropWhile (/=':') a
split_argument [] = Left "Argument is missing"
split_argument _  = Left "Too many arguments"

main :: IO ()
main = do
  -- get arguments
  args <- getArgs
  let x = split_argument args
  case split_argument args of
    -- if it is not good, display usage
    Left msg -> hPutStrLn stderr msg
    Right (pgfname,langname) ->
        do
          -- open PGF and language
          pgf <- readPGF pgfname
          case readLanguage langname of
            Nothing -> hPutStrLn stderr "Unknown language name"
            Just lang -> interact (translate pgf lang)


translate :: PGF -> Language -> String -> String
translate pgf lang = unlines . map translate' . lines  
    where translate' s = case parse pgf lang (startCat pgf) s of
                           [] -> "*** no parse ***"
                           t:_ -> linearize pgf lang t