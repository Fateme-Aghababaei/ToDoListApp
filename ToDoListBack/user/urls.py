from django.urls import path
from .views import *

urlpatterns = [
    path('login/', login),
    path('logout/', logout),
    path('signup/', signup),
    path('get_user/', get_user),
    path('edit_profile_photo/', edit_profile_photo),
    path('edit_profile/', edit_profile),
    path('change_password/', change_password),
]
