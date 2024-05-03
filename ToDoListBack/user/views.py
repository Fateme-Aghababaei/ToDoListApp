from rest_framework.decorators import api_view, permission_classes
from rest_framework import permissions
from django.contrib.auth.models import User
from .serializers import LoginSerializer, UserSerializer, EditUserSerializer, ChangePasswordSerializer
from rest_framework.response import Response
from django.contrib.auth import authenticate
from rest_framework.authtoken.models import Token
from rest_framework import status
import os

@api_view(['POST'])
@permission_classes((permissions.AllowAny,))
def login(request):
    serializer = LoginSerializer(data=request.data)
    if serializer.is_valid():
        email = serializer.validated_data['email']
        password = serializer.validated_data['password']
        user = authenticate(username=email, password=password)
        if user:
            token, created = Token.objects.get_or_create(user=user)
            return Response({
                'token': token.key,
                'username': email
            }, status.HTTP_200_OK)
    return Response({'error': 'کاربر یافت نشد.'}, status.HTTP_404_NOT_FOUND)


@api_view(['POST'])
def logout(request):
    user = request.user
    Token.objects.get(user_id=user.id).delete()
    return Response({}, status.HTTP_200_OK)


@api_view(['POST'])
@permission_classes((permissions.AllowAny,))
def signup(request):
    serializer = LoginSerializer(data=request.data)
    if serializer.is_valid():
        email = serializer.validated_data['email']
        password = serializer.validated_data['password']
        if User.objects.filter(username=email):
            return Response({"error": "Username already exists."}, status.HTTP_409_CONFLICT)
        user = User.objects.create_user(username=email, email=email, password=password)
        token, created = Token.objects.get_or_create(user=user)
        return Response({
            'token': token.key, 
            'username': email,
            'message': 'حساب کاربری با موفقیت ایجاد شد.'
        })
    return Response({'error': 'کاربر یافت نشد.'}, status.HTTP_404_NOT_FOUND)


@api_view(['GET'])
def get_user(request):
    user = request.user
    serializer = UserSerializer(instance=user)
    return Response(serializer.data, status.HTTP_200_OK)


@api_view(['POST'])
def edit_profile_photo(request):
    user = request.user
    photo = request.FILES.get('photo')
    prev_photo_path = user.profile.photo.path if user.profile.photo else ""
    if not photo:
        user.profile.photo = None
    else:
        user.profile.photo = photo
    user.save()
    if prev_photo_path:
        os.remove(prev_photo_path)
    
    serializer = UserSerializer(instance=user)
    return Response(serializer.data, status.HTTP_200_OK)

@api_view(['POST'])
def edit_profile(request):
    user = request.user
    serializer = EditUserSerializer(data=request.data)
    if serializer.is_valid():
        username = serializer.validated_data['username']
        if user.username != username:
            user.username = username
        user.first_name = serializer.validated_data['first_name']
        user.save()
        serializer = UserSerializer(instance=user)
        return Response(serializer.data, status.HTTP_200_OK)
    return Response(serializer.errors, status.HTTP_400_BAD_REQUEST)

@api_view(['POST'])
def change_password(request):
    user: User = request.user
    serializer = ChangePasswordSerializer(data=request.data)
    if serializer.is_valid():
        if user.check_password(serializer.validated_data['old_pass']):
            user.set_password(serializer.validated_data['new_pass'])
            user.save()
            return Response({'message': 'گذرواژه با موفقیت تغییر کرد.'},status.HTTP_200_OK)
        else:
            return Response({'error':'گذرواژه قبلی نامعتبر است.'},status.HTTP_400_BAD_REQUEST)
    return Response(serializer.errors,status.HTTP_400_BAD_REQUEST)