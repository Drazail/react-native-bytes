/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React, {useState} from 'react';
import {Text, PermissionsAndroid, Button} from 'react-native';

import RNBytes from 'react-native-bytes';


async function requestPermission1() {
  try {
    const granted = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.READ_EXTERNAL_STORAGE,
      {
        title: 'RNBytes STORAGE Permission',
        message:
          'RNBytes needs STORAGE permision to hash local files ',
        buttonPositive: 'OK',
      },
    );
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      console.log('Hack away!');
    } else {
      console.log('YOU SHALL NOT HACK!');
    }
}catch(e){
  console.log(e)
}
}

async function requestPermission2() {
  try {
    const granted = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
      {
        title: 'RNBytes WRITE Permission',
        message:
          'RNBytes needs STORAGE permision to hash local files ',
        buttonPositive: 'OK',
      },
    );
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      console.log('Hack away!');
    } else {
      await requestPermission2()
    }
}catch(e){
  console.log(e)
}}

const App: () => React$Node = () => {
  requestPermission1().then(()=>requestPermission2().then(()=>{
    RNBytes.getFileLength("/storage/emulated/0/Download/05. Crown of Amber Canopy.mp3").then((a)=>console.log(a)).catch(e=>console.log(e))
    RNBytes.splitFile("/storage/emulated/0/Download/05. Crown of Amber Canopy.mp3","/storage/emulated/0/Download/spilit3",0,1024).then(a=>console.log(a)).catch(e=>console.log(e))
  })).catch(e=>console.log(e))
  
  return (
    <>
      <Text>coming soon</Text>
    </>
  );
};

export default App;
