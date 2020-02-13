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
        message: 'RNBytes needs STORAGE permision to hash local files ',
        buttonPositive: 'OK',
      },
    );
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      console.log('Hack away!');
    } else {
      console.log('YOU SHALL NOT HACK!');
    }
  } catch (e) {
    console.log(e);
  }
}

async function requestPermission2() {
  try {
    const granted = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.WRITE_EXTERNAL_STORAGE,
      {
        title: 'RNBytes WRITE Permission',
        message: 'RNBytes needs STORAGE permision to hash local files ',
        buttonPositive: 'OK',
      },
    );
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      console.log('Hack away!');
    } else {
      console.log('YOU SHALL NOT HACK!');
    }
  } catch (e) {
    console.log(e);
  }
}

const App: () => React$Node = () => {
  return (
    <>
      <Button title="requestReadPermision" onPress={() => requestPermission1()}>
        requestReadPermision
      </Button>
      <Button
        title="requestWritePermision"
        onPress={() => requestPermission2()}>
        requestWritePermision
      </Button>
      <Button
        title="getFileSize"
        onPress={() => {
          RNBytes.getFileLength(
            '/storage/emulated/0/Download/05. Crown of Amber Canopy.mp3',
          )
            .then(a => console.log(a))
            .catch(e => console.log(e));
        }}>
        getFileSize
      </Button>
      <Button
        title="splitFile"
        onPress={() =>
          RNBytes.getFileLength(
            '/storage/emulated/0/Download/05. Crown of Amber Canopy.mp3',
          )
            .then(l =>
              RNBytes.readFromAndWriteTo(
                '/storage/emulated/0/Download/05. Crown of Amber Canopy.mp3',
                '/storage/emulated/0/Download/spilit13',
                true,
                false,
                0,
                l / 3 - 1,
              ).then(() =>
                RNBytes.readFromAndWriteTo(
                  '/storage/emulated/0/Download/05. Crown of Amber Canopy.mp3',
                  '/storage/emulated/0/Download/spilit23',
                  true,
                  false,
                  l / 3,
                  l - 1,
                ),
              ),
            )
            .catch(e => console.log(e))
        }>
        splitFile
      </Button>

      <Button
        title="reAttachFiles"
        onPress={() =>
          RNBytes.getFileLength('/storage/emulated/0/Download/spilit1')
            .then(l =>
              RNBytes.readFromAndWriteTo(
                '/storage/emulated/0/Download/spilit13',
                '/storage/emulated/0/Download/patched3',
                true,
                true,
                0,
                l - 1,
              ),
            )
            .then(() =>
              RNBytes.getFileLength('/storage/emulated/0/Download/spilit1'),
            )
            .then(l =>
              RNBytes.readFromAndWriteTo(
                '/storage/emulated/0/Download/spilit23',
                '/storage/emulated/0/Download/patched3',
                true,
                true,
                0,
                l - 1,
              ),
            )
            .catch(e => console.log(e))
        }>
        splitFile
      </Button>
    </>
  );
};

export default App;
