from fastapi import FastAPI
from view import empresa_routes

app = FastAPI()

app.include_router(empresa_routes.router)
